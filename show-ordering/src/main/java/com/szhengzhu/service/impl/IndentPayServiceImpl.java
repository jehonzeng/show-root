package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.*;
import com.szhengzhu.bean.ordering.*;
import com.szhengzhu.bean.ordering.param.IndentPayParam;
import com.szhengzhu.bean.ordering.vo.IndentPayVo;
import com.szhengzhu.bean.ordering.vo.PayBaseVo;
import com.szhengzhu.bean.xwechat.vo.CalcVo;
import com.szhengzhu.bean.xwechat.vo.PayTypeModel;
import com.szhengzhu.bean.xwechat.vo.UnifiedIndent;
import com.szhengzhu.feign.ShowMemberClient;
import com.szhengzhu.code.IndentStatus;
import com.szhengzhu.code.PayBaseCode;
import com.szhengzhu.code.PayTypeCode;
import com.szhengzhu.code.TableStatus;
import com.szhengzhu.core.*;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.*;
import com.szhengzhu.rabbitmq.Sender;
import com.szhengzhu.redis.Redis;
import com.szhengzhu.service.IndentPayService;
import com.szhengzhu.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service("indentPayService")
public class IndentPayServiceImpl implements IndentPayService {

    @Resource
    private Redis redis;

    @Resource
    private Sender sender;

    @Resource
    private PayMapper payMapper;

    @Resource
    private TableMapper tableMapper;

    @Resource
    private IndentMapper indentMapper;

    @Resource
    private IndentPayMapper indentPayMapper;

    @Resource
    private PayBackMapper payBackMapper;

    @Resource
    private PayRefundMapper payRefundMapper;

    @Resource
    private UserTicketMapper userTicketMapper;

    @Resource
    private IndentDetailMapper indentDetailMapper;

    @Resource
    private TemplateCommodityMapper templateCommodityMapper;

    @Resource
    private ShowMemberClient showMemberClient;

    @Override
    public IndentPay getInfo(String indentPayId) {
        return indentPayMapper.selectByPrimaryKey(indentPayId);
    }

    @Transactional
    @Override
    public String resPay(IndentPayParam payParam) {
        String indentId = payParam.getIndentId();
        Indent indent = indentMapper.selectByPrimaryKey(indentId);
        BigDecimal payTotal = indentPayMapper.selectPayTotal(indentId);
        ShowAssert.checkTrue(indent.getIndentTotal().compareTo(payTotal) == 0, StatusCode._4042);
        ShowAssert.checkTrue(indent.getIndentTotal().compareTo(payTotal.add(payParam.getPayAmount())) < 0, StatusCode._4043);
        Pay pay = payMapper.selectByPrimaryKey(payParam.getPayId());
        IndentPay indentPay = createPay(payParam);
        indentPay.setPayName(pay.getName());
        // 记录实收金额
        if (Boolean.TRUE.equals(pay.getReceived())) {
            indentPay.setAmount(indentPay.getPayAmount());
        }
        indentPayMapper.insertSelective(indentPay);
        return indent.getIndentNo();
    }

    private IndentPay createPay(IndentPayParam payParam) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        return IndentPay.builder()
                .markId(snowflake.nextIdStr()).indentId(payParam.getIndentId())
                .payId(payParam.getPayId()).quantity(1).amount(BigDecimal.ZERO).payAmount(payParam.getPayAmount())
                // 券
                .discountId(payParam.getDiscountId())
                .userId(payParam.getUserId()).employeeId(payParam.getEmployeeId()).code(payParam.getCode())
                .createTime(DateUtil.date()).consumptionId(payParam.getConsumptionId())
                // 设置为支付有效
                .status(1).build();
    }

    @Transactional
    @Override
    public void resCouponPay(IndentPayParam payParam) {
        String indentId = payParam.getIndentId();
        BigDecimal payTotal = indentPayMapper.selectPayTotal(indentId);
        BigDecimal indentTotal = indentMapper.selectTotalById(indentId);
        ShowAssert.checkTrue(indentTotal.compareTo(payTotal) == 0, StatusCode._4042);
        ShowAssert.checkTrue(indentTotal.compareTo(payTotal.add(payParam.getPayAmount())) < 0, StatusCode._4043);
        Pay pay = payMapper.selectByPrimaryKey(payParam.getPayId());
        ShowAssert.checkTrue(ObjectUtil.isNull(pay) || (pay.getDiscountLimit() != null && pay.getDiscountLimit().compareTo(indentTotal) > 0), StatusCode._4048);
        BigDecimal discount = BigDecimal.ZERO;
        if (pay.getDiscountType().equals(0)) {
            discount = pay.getDiscountAmount();
        } else if (pay.getDiscountType().equals(1)) {
            BigDecimal currDiscount = NumberUtil.sub(1, NumberUtil.div(pay.getDiscountAmount(), 10));
            discount = NumberUtil.mul(indentTotal, currDiscount);
        }
        IndentPay indentPay = createPay(payParam);
        indentPay.setPayAmount(NumberUtil.round(discount, 0, RoundingMode.DOWN));
        indentPay.setPayName(pay.getName());
        indentPayMapper.insertSelective(indentPay);
    }

    @Override
    public void resVoucherPay(IndentPayParam payParam) {
        int quantity = payParam.getPayAmount().intValue();
        Pay pay = payMapper.selectByPrimaryKey(payParam.getPayId());
        IndentPay indentPay = createPay(payParam);
        // 支付名称
        indentPay.setPayName(pay.getName());
        indentPay.setQuantity(quantity);
        if (Boolean.TRUE.equals(pay.getReceived())) {
            indentPay.setAmount(pay.getReceiveAmount().multiply(new BigDecimal(quantity)));
        }
        indentPay.setPayAmount(pay.getDiscountAmount().multiply(new BigDecimal(quantity)));
        indentPayMapper.insertSelective(indentPay);
    }

    @Transactional
    @Override
    public void resTicketPay(IndentPayParam payParam) {
        String indentId = payParam.getIndentId();
        UserTicket userTicket = userTicketMapper.selectUserTicket(payParam.getDiscountId());
        Indent indent = indentMapper.selectByPrimaryKey(indentId);
        BigDecimal indentTotal = indent.getIndentTotal();
        BigDecimal payTotal = indentPayMapper.selectPayTotal(indentId);
        indentTotal = indentTotal.subtract(payTotal);
        ShowAssert.checkNull(userTicket, StatusCode._4048);
        ShowAssert.checkTrue((userTicket.getLimitPrice() != null && userTicket.getLimitPrice().compareTo(indentTotal) > 0), StatusCode._4048);
        List<IndentDetail> detailList = indentDetailMapper.selectByIndent(indentId);
        // 判断该券有没有商品限制
        BigDecimal discount = calcUserTicket(userTicket, detailList, indentTotal);
        if (discount.compareTo(BigDecimal.ZERO) > 0) {
            IndentPay indentPay = createPay(payParam);
            indentPay.setPayId(PayBaseCode.TICKET_PAY.code);
            indentPay.setPayName(userTicket.getName());
            indentPay.setQuantity(1);
            indentPay.setAmount(BigDecimal.ZERO);
            indentPay.setPayAmount(discount);
            indentPayMapper.insertSelective(indentPay);
            userTicket.setUseTime(DateUtil.date());
            userTicket.setStatus(0);
            userTicketMapper.updateByPrimaryKeySelective(userTicket);
            tableMapper.updateTableStatus(indent.getTableId(), TableStatus.PAID.code);
        }
    }

    /**
     * 券计算优惠总额
     *
     * @param userTicket 会员券信息
     * @param detailList 订单信息
     * @param total      剩余支付金额
     * @return 返回优惠金额
     */
    private BigDecimal calcUserTicket(UserTicket userTicket, List<IndentDetail> detailList, BigDecimal total) {
        List<String> commIds = templateCommodityMapper.selectCommodityIds(userTicket.getTemplateId());
        BigDecimal discount = BigDecimal.ZERO;
        // 判断该券有没有商品限制
        if (commIds.isEmpty()) {
            if ("0".equals(String.valueOf(userTicket.getType()))) {
                discount = userTicket.getDiscount();
            } else if ("1".equals(String.valueOf(userTicket.getType()))) {
                BigDecimal currDiscount = NumberUtil.sub(1, NumberUtil.div(userTicket.getDiscount(), 10));
                discount = NumberUtil.round(NumberUtil.mul(total, currDiscount), 0, RoundingMode.DOWN);
            }
            return discount;
        }
        List<IndentDetail> indentDetails = detailList.stream()
                .filter(detail -> commIds.contains(detail.getCommodityId()) && !"-1".equals(String.valueOf(detail.getStatus())))
                .collect(Collectors.toList());
        for (IndentDetail detail : indentDetails) {
            int quantity = detail.getQuantity();
            BigDecimal salePrice = detail.getSalePrice();
            BigDecimal costPrice = detail.getCostPrice();
            BigDecimal diffPrice = BigDecimal.ZERO;
            if ("0".equals(String.valueOf(userTicket.getType()))) {
                diffPrice = salePrice.subtract(costPrice.subtract(userTicket.getDiscount()));
            } else if ("1".equals(String.valueOf(userTicket.getType()))) {
                BigDecimal discountPrice = NumberUtil.mul(costPrice, NumberUtil.div(userTicket.getDiscount(), 10));
                diffPrice = NumberUtil.sub(salePrice, discountPrice);
            }
            // 当优惠券只是单个商品的优惠活动时，只计算优惠一个商品即可
            if (commIds.size() == 1) {
                discount = diffPrice;
                break;
            } else {
                discount = discount.add(diffPrice.multiply(new BigDecimal(quantity)));
            }
        }
        return discount;
    }

    @Transactional
    @Override
    public void deletePay(String markId, String employeeId) {
        IndentPay indentPay = indentPayMapper.selectByPrimaryKey(markId);
        PayBaseVo pay = payMapper.selectById(indentPay.getPayId());
        String payCode = pay.getCode();
        if (PayTypeCode.MEMBER.equals(payCode)) {
            // 退会员金额
            sender.addMemberDetail(indentPay.getConsumptionId(), indentPay.getUserId(), indentPay.getIndentId());
        } else if (PayTypeCode.TICKET.equals(payCode)) {
            // 用户代金券返还
            userTicketMapper.cancelUse(indentPay.getDiscountId());
        } else if (PayTypeCode.CASH.equals(payCode) && !StringUtils.isEmpty(indentPay.getCode())
                && indentPay.getStatus().equals(1)) {
            // 微信支付返还
            sender.indentRefund(markId);
        }
        indentPay.setEmployeeId(employeeId);
        indentPay.setModifyTime(DateUtil.date());
        indentPay.setStatus(-1);
        indentPayMapper.updateByPrimaryKeySelective(indentPay);
        indentMapper.updateIndentStatus(indentPay.getIndentId(), IndentStatus.CREATE.code);
        Indent indent = indentMapper.selectByPrimaryKey(indentPay.getIndentId());
        System.out.println(indent);
        String tableId = indentMapper.selectTableIdByIndentId(indentPay.getIndentId());
        tableMapper.updateTableStatus(tableId, TableStatus.EATING.code);
//        sender.memberConsumeRefund(indentPay.getIndentId());
    }

    public CalcVo xcalc(UnifiedIndent indentCalc) {
        CalcVo calc = new CalcVo();
        List<PayTypeModel> list = new LinkedList<>();
        String indentId = indentCalc.getIndentId();
        Indent indent = indentMapper.selectByPrimaryKey(indentId);
        // 订单商品总金额（无任何优惠）
        BigDecimal costTotal = indentDetailMapper.selectCostTotal(indentId);
        // 订单金额
        BigDecimal total = indent.getIndentTotal();
        // 已支付的金额
        BigDecimal discount = xcalcUserTicket(indentCalc, total, null, false);
        BigDecimal memberDiscount = addPayDiscount(indentId, indentCalc.getMemberId(), discount);
        total = total.subtract(discount);
        BigDecimal payTotal = indentPayMapper.selectPayTotalByUser(indentId, indentCalc.getUserId(), PayBaseCode.MEMBER_DISCOUNT.code);
        total = total.subtract(payTotal);
        BigDecimal saleTotal = indentDetailMapper.selectIndentTotal(indentId);
        total = total.subtract(memberDiscount);
        list.add(PayTypeModel.builder().name("商品金额").key("costTotal").value(costTotal).build());
        list.add(PayTypeModel.builder().name("商品优惠").key("commDiscount").value(costTotal.subtract(saleTotal)).build());
        list.add(PayTypeModel.builder().name("会员折扣").key("memberDiscount").value(memberDiscount).build());
        list.add(PayTypeModel.builder().name("优惠券").key("ticketDiscount").value(discount).build());
        list.add(PayTypeModel.builder().name("其他").key("otherTotal").value(payTotal).build());
        calc.setTotal(total);
        calc.setDiscountList(list);
        return calc;
    }

    public BigDecimal addPayDiscount(String indentId, String memberId, BigDecimal discount) {
        Indent indent = indentMapper.selectByPrimaryKey(indentId);
        List<IndentDetail> details = indentDetailMapper.selectDiscountProduct(indentId);
        List<IndentPayVo> payList = indentPayMapper.selectPayDiscount(indentId);
        if (StrUtil.isNotEmpty(memberId) && discount.compareTo(BigDecimal.ZERO) == 0 && ObjectUtil.isEmpty(payList) && ObjectUtil.isEmpty(details)) {
            BigDecimal memberDiscount = showMemberClient.selectMemberDiscount(memberId).getData();
            if (memberDiscount.compareTo(BigDecimal.ZERO) != 0) {
                BigDecimal memberDiscountPrice = NumberUtil.round(indentDetailMapper.selectDiscountPrice(indentId, NumberUtil.div(memberDiscount, 10)), 0, RoundingMode.DOWN);
                if (ObjectUtil.isEmpty(indentPayMapper.selectByPayId(indentId, PayBaseCode.MEMBER_DISCOUNT.code))) {
                    Snowflake snowflake = IdUtil.getSnowflake(1, 1);
                    IndentPay indentPay = IndentPay.builder().markId(snowflake.nextIdStr()).indentId(indentId).amount(BigDecimal.ZERO).
                            payId(PayBaseCode.MEMBER_DISCOUNT.code).payName(PayBaseCode.getValue(PayBaseCode.MEMBER_DISCOUNT.code)).
                            quantity(1).payAmount(indent.getIndentTotal().subtract(memberDiscountPrice)).createTime(DateUtil.date())
                            // 设置为支付有效
                            .status(1).build();
                    indentPayMapper.insertSelective(indentPay);
                } else {
                    indentPayMapper.updateMemberDiscount(indentId, PayBaseCode.MEMBER_DISCOUNT.code, 1, indent.getIndentTotal().subtract(memberDiscountPrice));
                }
                return indent.getIndentTotal().subtract(memberDiscountPrice);
            } else {
                indentPayMapper.updateMemberDiscount(indentId, PayBaseCode.MEMBER_DISCOUNT.code, -1, null);
            }
        } else {
            indentPayMapper.updateMemberDiscount(indentId, PayBaseCode.MEMBER_DISCOUNT.code, -1, null);
        }
        return BigDecimal.ZERO;
    }

    @Transactional
    @Override
    public BigDecimal xwechatPay(UnifiedIndent unifiedIndent) {
        String indentId = unifiedIndent.getIndentId();
        // 清理掉未入账的支付记录
        clearOtherPay(indentId);
        Indent indent = indentMapper.selectByPrimaryKey(indentId);
        ShowAssert.checkTrue(IndentStatus.BILL.code.equals(indent.getIndentStatus()), StatusCode._5006);
        BigDecimal payTotal = indentPayMapper.selectPayTotal(indentId);
        BigDecimal total = indent.getIndentTotal();
        // 计算优惠券总优惠
        BigDecimal discount = xcalcUserTicket(unifiedIndent, total, 0, true);
        indent.setIndentStatus(IndentStatus.PAYING.code);
        indent.setModifyTime(DateUtil.date());
        if (discount.compareTo(total.subtract(payTotal)) == 0) {
            indent.setIndentStatus(IndentStatus.BILL.code);
        } else {
            total = total.subtract(discount.add(payTotal));
            xcreatePay(unifiedIndent, null, total, PayBaseCode.WECHAT_PAY.code, PayBaseCode.getValue(PayBaseCode.WECHAT_PAY.code), 0);
        }
        indentMapper.updateByPrimaryKeySelective(indent);
        tableMapper.updateTableStatus(indent.getTableId(), TableStatus.PAID.code);
        return total;
    }

    /**
     * 计算用户所选代金券的优惠金额
     *
     * @param unifiedIndent 下单参数
     * @param total         剩余支付金额
     * @param status        1 插入即为有效  0  插入为待确认有效
     * @param insertFlag    是否插入标识
     * @return 返回优惠金额
     */
    private BigDecimal xcalcUserTicket(UnifiedIndent unifiedIndent, BigDecimal total, Integer status,
                                       boolean insertFlag) {
        BigDecimal discount = BigDecimal.ZERO;
        if (StrUtil.isEmpty(unifiedIndent.getTicketId())) {
            return discount;
        }
        UserTicket ticket = userTicketMapper.selectUserTicket(unifiedIndent.getTicketId());
        if (ObjectUtil.isNotNull(ticket) && ticket.getLimitPrice() != null && total.compareTo(ticket.getLimitPrice()) >= 0) {
            List<IndentDetail> detailList = indentDetailMapper.selectByIndent(unifiedIndent.getIndentId());
            discount = calcUserTicket(ticket, detailList, total);
            if (discount.compareTo(total) > 0) {
                discount = total;
            }
        }
        if (ObjectUtil.isNotNull(ticket) && discount.compareTo(BigDecimal.ZERO) > 0 && insertFlag) {
            xcreatePay(unifiedIndent, ticket.getMarkId(), discount, PayBaseCode.TICKET_PAY.code, ticket.getName(), status);
            ticket.setUseTime(DateUtil.date());
            ticket.setStatus(0);
            userTicketMapper.updateByPrimaryKeySelective(ticket);
        }
        return discount;
    }

    /**
     * 将代金券所支付金额添加到订单支付记录
     *
     * @param unifiedIndent 支付参数
     * @param couponId      代金券  ID
     * @param amount        金额
     * @param payId         支付方式ID
     * @param payName       支付金额
     * @param status        值为1时，即时生效，值为0时，即微信发起支付时添加到支付记录的值，待支付回滚后将该值改为1，该记录才生效
     */
    private void xcreatePay(UnifiedIndent unifiedIndent, String couponId, BigDecimal amount, String payId,
                            String payName, Integer status) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        IndentPay indentPay = IndentPay.builder()
                .markId(snowflake.nextIdStr()).indentId(unifiedIndent.getIndentId())
                .payId(payId).payName(payName).quantity(1).payAmount(amount)
                // 券
                .discountId(couponId)
                .userId(unifiedIndent.getUserId()).code(unifiedIndent.getCode()).createTime(DateUtil.date())
                .consumptionId(unifiedIndent.getConsumptionId())
                // 设置为支付有效
                .status(status).build();
        if (PayBaseCode.TICKET_PAY.code.equals(payId)) {
            indentPay.setAmount(BigDecimal.ZERO);
        } else {
            indentPay.setAmount(amount);
        }
        indentPayMapper.insertSelective(indentPay);
    }

    private void clearOtherPay(String indentId) {
        List<IndentPay> payList = indentPayMapper.selectOtherPay(indentId);
        for (IndentPay pay : payList) {
            if (StrUtil.isEmpty(pay.getDiscountId())) {
                userTicketMapper.cancelUse(pay.getDiscountId());
            }
            indentPayMapper.updateStatusById(pay.getMarkId(), -1);
        }
    }

    @Transactional
    @Override
    public void xmemberPay(UnifiedIndent unifiedIndent) {
        String indentId = unifiedIndent.getIndentId();
        // 清理掉未入账的支付记录
        clearOtherPay(indentId);
        Indent indent = indentMapper.selectByPrimaryKey(indentId);
        if (IndentStatus.BILL.code.equals(indent.getIndentStatus())) {
            sender.addMemberDetail(unifiedIndent.getConsumptionId(), unifiedIndent.getUserId(), indentId);
        }
        ShowAssert.checkTrue(IndentStatus.BILL.code.equals(indent.getIndentStatus()), StatusCode._5006);
        BigDecimal total = indent.getIndentTotal();
        BigDecimal payTotal = indentPayMapper.selectPayTotal(indentId);
        // 计算优惠券总优惠
        BigDecimal discount = xcalcUserTicket(unifiedIndent, total.subtract(payTotal), 1, true);
        if (discount.compareTo(total.subtract(payTotal)) < 0) {
            total = total.subtract(discount.add(payTotal));
            xcreatePay(unifiedIndent, null, total, PayBaseCode.MEMBER_PAY.code, PayBaseCode.getValue(PayBaseCode.MEMBER_PAY.code), 1);
        }
        indent.setIndentStatus(IndentStatus.BILL.code);
        indent.setModifyTime(DateUtil.date());
        indent.setMemberId(unifiedIndent.getMemberId());
        indentMapper.updateByPrimaryKeySelective(indent);
        tableMapper.updateTableStatus(indent.getTableId(), TableStatus.BILL.code);
        sender.sendMatchChance(null, indent.getMemberId(), indent.getIndentTotal());
    }

    @Override
    public void xwechatBack(String indentId) {
        String payKey = "indent:pay:" + indentId;
        Object userId = redis.get(payKey);
        if (ObjectUtil.isNotNull(userId)) {
            Indent indent = indentMapper.selectByPrimaryKey(indentId);
            indentMapper.updateIndentStatus(indentId, IndentStatus.BILL.code);
            tableMapper.updateTableStatus(indent.getTableId(), TableStatus.BILL.code);
            indentPayMapper.updateStatusByUser(indentId, (String) userId, 1);
            sender.sendMatchChance((String) userId, null, indent.getIndentTotal());
        }
        redis.del(payKey);
    }

    @Override
    public void addPayBack(PayBack payBack) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        payBack.setMarkId(snowflake.nextIdStr());
        payBack.setAddTime(DateUtil.date());
        payBackMapper.insertSelective(payBack);
    }

    @Override
    public void addRefundBack(PayRefund payRefund) {
        PayRefund refund = payRefundMapper.selectByNo(payRefund.getRefundNo());
        if (ObjectUtil.isNotNull(refund)) {
            refund.setBackStatus(payRefund.getBackStatus());
            refund.setBackInfo(payRefund.getBackInfo());
            refund.setRefundInfo(payRefund.getRefundInfo());
            payRefundMapper.updateByPrimaryKeySelective(refund);
        } else {
            Snowflake snowflake = IdUtil.getSnowflake(1, 1);
            payRefund.setMarkId(snowflake.nextIdStr());
            payRefund.setCreateTime(DateUtil.date());
            payRefundMapper.insertSelective(payRefund);
        }
        if (!payRefund.getRefundStatus().equals(1)) {
            // 退款失败
            indentPayMapper.updateStatusById(payRefund.getPayId(), 1);
            IndentPay indentPay = indentPayMapper.selectByPrimaryKey(payRefund.getPayId());
            // 判断是否结算完
            indentStatusConfirm(indentPay.getIndentId());
        }
    }

    private void indentStatusConfirm(String indentId) {
        Indent indent = indentMapper.selectByPrimaryKey(indentId);
        BigDecimal payTotal = indentPayMapper.selectPayTotal(indentId);
        if (indent.getIndentTotal().compareTo(payTotal) == 0) {
            indentMapper.updateIndentStatus(indentId, IndentStatus.BILL.code);
            tableMapper.updateTableStatus(indent.getTableId(), TableStatus.BILL.code);
        } else {
            indentMapper.updateIndentStatus(indentId, IndentStatus.PAYING.code);
            tableMapper.updateTableStatus(indent.getTableId(), TableStatus.PAID.code);
        }
    }


    @Override
    public String getRefundNo(String payId) {
        return payRefundMapper.selectNoByPay(payId);
    }

    @Override
    public BigDecimal selectAmount(String indentId) {
        return indentPayMapper.selectAmount(indentId);
    }
}
