package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.member.MemberAccount;
import com.szhengzhu.client.ShowMemberClient;
import com.szhengzhu.bean.member.IntegralDetail;
import com.szhengzhu.bean.ordering.*;
import com.szhengzhu.bean.ordering.param.*;
import com.szhengzhu.bean.ordering.print.PrinterCode;
import com.szhengzhu.bean.ordering.vo.*;
import com.szhengzhu.bean.xwechat.vo.*;
import com.szhengzhu.code.*;
import com.szhengzhu.code.TableStatus;
import com.szhengzhu.core.*;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.*;
import com.szhengzhu.print.PrintService;
import com.szhengzhu.rabbitmq.Sender;
import com.szhengzhu.redis.Redis;
import com.szhengzhu.service.IndentService;
import com.szhengzhu.util.ShowUtils;
import com.szhengzhu.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * @author Administrator
 */
@Service("indentService")
public class IndentServiceImpl implements IndentService {

    @Resource
    private Redis redis;

    @Resource
    private Sender sender;

    @Resource
    private CartMapper cartMapper;

    @Resource
    private TableMapper tableMapper;

    @Resource
    private IndentMapper indentMapper;

    @Resource
    private IndentPayMapper indentPayMapper;

    @Resource
    private CommodityMapper commodityMapper;

    @Resource
    private IndentDetailMapper indentDetailMapper;

    @Resource
    private CommodityItemMapper commodityItemMapper;

    @Resource
    private CommodityPriceMapper commodityPriceMapper;

    @Resource
    private DiscountInfoMapper discountInfoMapper;

    @Resource
    private PrintService printService;

    @Resource
    private MarketInfoMapper marketInfoMapper;

    @Resource
    private IndentRemarkMapper indentRemarkMapper;

    @Resource
    private ShowMemberClient showMemberClient;

    @Override
    public Indent getInfoByNo(String indentNo) {
        return indentMapper.selectByNo(indentNo);
    }

    @Transactional
    @Override
    public List<String> createBatch(IndentParam indentParam) {
        String tableId = indentParam.getTableId();
        Table table = tableMapper.selectByPrimaryKey(tableId);
        ShowAssert.checkTrue(TableStatus.FREEING.code.equals(table.getTableStatus()) || StrUtil.isEmpty(table.getTempNum()), StatusCode._5025);
        Indent indent = createIndent(table);
        indent.setEmployeeId(indentParam.getEmployeeId());
        List<IndentDetail> detailList = new LinkedList<>();
        BigDecimal indentTotal = ObjectUtil.isNull(indent.getIndentTotal()) ? BigDecimal.ZERO : indent.getIndentTotal();
        String timeCode = ShowUtils.createTempnum();
        indentTotal = createIndentDetail(indentParam.getDetailList(), timeCode, indentTotal, indent.getMarkId(), indentParam.getEmployeeId(), detailList);
        indent.setIndentTotal(indentTotal);
        indentMapper.insertSelective(indent);
        indentDetailMapper.insertBatch(detailList);
        // 查小程序是否存在未确认的订单
        Integer exist = indentDetailMapper.selectExistNoCookTime(indent.getMarkId());
        // 修改餐桌状态
        if (exist == null) {
            tableMapper.updateTableStatus(tableId, TableStatus.EATING.code);
        }
        // 发送打印队列
        return printService.orderOrChange(indent.getMarkId(), timeCode,
                indentParam.getEmployeeId(), null, table.getStoreId());
    }

    /**
     * 点餐平台结算商品详情，计算订单金额，并将商品信息添加到订单商品列表
     *
     * @param commList
     * @param timeCode
     * @param indentTotal
     * @param indentId
     * @param detailList
     * @return
     */
    private BigDecimal createIndentDetail(List<DetailParam> commList, String timeCode, BigDecimal indentTotal,
                                          String indentId, String creator, List<IndentDetail> detailList) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        List<DetailParam> list = commList.stream().filter(comm -> comm.getQuantity() > 0).collect(Collectors.toList());
        for (DetailParam comm : list) {
            Commodity commodity = commodityMapper.selectByPrimaryKey(comm.getCommodityId());
            CommodityPrice commodityPrice = commodityPriceMapper.selectByIdOrDefault(comm.getPriceId());
            BigDecimal markupPrice = commodityItemMapper.sumItemPrice(commodity.getMarkId(), comm.getSpecsItems());
            BigDecimal price = commodityPrice.getSalePrice().add(markupPrice);
            IndentDetail detail = IndentDetail.builder()
                    .markId(snowflake.nextIdStr()).timeCode(timeCode).indentId(indentId).commodityType(commodity.getType())
                    .commodityId(comm.getCommodityId()).commodityName(commodity.getName()).priceId(comm.getPriceId())
                    .specsItems(comm.getSpecsItems()).quantity(comm.getQuantity()).costPrice(price).priceType(0)
                    .salePrice(price).integralPrice(0).creator(creator)
                    .createTime(DateUtil.date()).status(1).build();
            detailList.add(detail);
            // 计算商品总价
            indentTotal = NumberUtil.add(indentTotal, NumberUtil.mul(detail.getSalePrice(), detail.getQuantity()));
        }
        // 去除小数部分
        return NumberUtil.round(indentTotal, 0, RoundingMode.DOWN);
    }

    /**
     * 封装订单数据 如果已存在订单 则返回该订单 如果不存在订单，则创建订单
     *
     * @param table 桌台信息
     * @return 返回订单信息
     */
    private Indent createIndent(Table table) {
        Indent indent = indentMapper.selectByTable(table.getMarkId(), table.getTempNum());
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        if (ObjectUtil.isNull(indent)) {
            String key = table.getMarkId() + ":" + table.getTempNum();
            Object lock = redis.get(key);
            ShowAssert.checkTrue(Boolean.TRUE.equals(lock), StatusCode._4007);
            redis.set(key, true, 30);
            indent = Indent.builder()
                    .markId(snowflake.nextIdStr()).indentNo(ShowUtils.createIndentNo()).storeId(table.getStoreId())
                    .tableId(table.getMarkId()).manNum(table.getManNum()).indentTime(DateUtil.date())
                    .indentStatus(IndentStatus.CREATE.code).tempNum(table.getTempNum()).build();
        }
        return indent;
    }

    @Transactional
    @Override
    public String create(String tableId, String userId) {
        Table table = tableMapper.selectByPrimaryKey(tableId);
        ShowAssert.checkTrue(TableStatus.FREEING.code.equals(table.getTableStatus()) || StrUtil.isEmpty(table.getTempNum()), StatusCode._5025);
        Indent indent = createIndent(table);
        ShowAssert.checkTrue(IndentStatus.PAYING.code.equals(indent.getIndentStatus()), StatusCode._4053);
        ShowAssert.checkTrue(IndentStatus.BILL.code.equals(indent.getIndentStatus()), StatusCode._4053);
        indent.setIndentUser(userId);
        Lock lock = new ReentrantLock();
        lock.lock();
        try {
            fromCartToIndent(tableId, indent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return indent.getMarkId();
    }

    /**
     * 将购物车商品添加到订单
     *
     * @param tableId 桌台ID
     * @param indent  订单信息
     */
    private void fromCartToIndent(String tableId, Indent indent) {
        List<IndentDetail> detailList = new LinkedList<>();
        BigDecimal indentTotal = ObjectUtil.isNull(indent.getIndentTotal()) ? BigDecimal.ZERO : indent.getIndentTotal();
        indentTotal = createIndentDetail(tableId, indent.getMarkId(), indentTotal, detailList);
        if (detailList.isEmpty()) {
            return;
        }
        indent.setIndentTotal(indentTotal);
        indentMapper.insertSelective(indent);
        indentDetailMapper.insertBatch(detailList);
        // 清空购物车
        cartMapper.clearCartByTable(tableId);
        // 修改餐桌状态
        tableMapper.updateTableStatus(tableId, TableStatus.ORDERING.code);
    }

    /**
     * 结算购物车商品，计算订单金额，并添加到订单商品列表
     *
     * @param tableId
     * @param indentId
     * @param indentTotal
     * @param detailList
     * @return
     */
    private BigDecimal createIndentDetail(String tableId, String indentId, BigDecimal indentTotal, List<IndentDetail> detailList) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        String timeCode = ShowUtils.createTempnum();
        List<Cart> cartList = cartMapper.selectByTable(tableId);
        cartList = cartList.stream().filter(cart -> cart.getQuantity() > 0).collect(Collectors.toList());
        for (Cart cart : cartList) {
            IndentDetail detail = IndentDetail.builder()
                    .markId(snowflake.nextIdStr()).timeCode(timeCode).indentId(indentId).commodityType(cart.getCommodityType())
                    .commodityId(cart.getCommodityId()).commodityName(cart.getCommodityName())
                    .priceId(cart.getPriceId()).specsItems(cart.getSpecsItems())
                    .quantity(cart.getQuantity()).costPrice(cart.getCostPrice()).userId(cart.getUserId())
                    .priceType(cart.getPriceType()).salePrice(cart.getSalePrice()).integralPrice(cart.getIntegralPrice())
                    .createTime(DateUtil.date()).status(0).build();
            detailList.add(detail);
            // 计算商品总价
            int quantity = detail.getQuantity();
            indentTotal = NumberUtil.add(indentTotal, NumberUtil.mul(detail.getSalePrice(), quantity));
        }
        return NumberUtil.round(indentTotal, 0, RoundingMode.DOWN);
    }

    @Transactional
    @Override
    public List<String> confirm(String tableId, String employeeId) {
        Table table = tableMapper.selectByPrimaryKey(tableId);
        tableMapper.updateTableStatus(tableId, TableStatus.EATING.code);
        // 获取用户下单信息
        Indent indent = indentMapper.selectByTable(tableId, table.getTempNum());
        String indentId = indent.getMarkId();
        String timeCodes = indentDetailMapper.selectNoCookTimeCode(indentId);
        List<String> logStamps = new ArrayList<>();
        if (!StrUtil.isEmpty(timeCodes)) {
            indentDetailMapper.updateStatus(indentId, timeCodes);
            // 发送打印队列
            logStamps = printService.orderOrChange(indent.getMarkId(), timeCodes, employeeId, null, table.getStoreId());
        }
        return logStamps;
    }

    @Override
    public PageGrid<IndentModel> pageBase(PageParam<String> param) {
        String sidx = "mark_id".equals(param.getSidx()) ? "i.indent_time " : param.getSidx();
        PageMethod.startPage(param.getPageIndex(), param.getPageSize());
        PageMethod.orderBy(sidx + " " + param.getSort());
        PageInfo<IndentModel> pageInfo = new PageInfo<>(indentMapper.selectXModelByUser(param.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public IndentModel getIndentModel(String indentId) {
        addPayDiscount(indentId);
        // 计算是否有买减活动
        calcIndentMarket(indentId);
        IndentModel indentModel = indentMapper.selectXModelById(indentId);
        List<IndentTimeModel> indentTimeList = indentDetailMapper.selectIndentTime(indentId);
        List<IndentRemark> remarkList = indentRemarkMapper.query(IndentRemark.builder().indentId(indentId).build());
        List<IndentPayVo> payList = indentPayMapper.selectIndentPayVo(indentId);
        indentModel.setTimeList(indentTimeList);
        indentModel.setRemarkList(remarkList);
        indentModel.setPayList(payList);
        // 清算订单成本
        sender.calcIndentBaseTotal(indentId);
        return indentModel;
    }

    public void addPayDiscount(String indentId) {
        Indent indent = indentMapper.selectByPrimaryKey(indentId);
        List<IndentDetail> details = indentDetailMapper.selectDiscountProduct(indentId);
        List<IndentPayVo> payList = indentPayMapper.selectPayDiscount(indentId);
        if (StrUtil.isNotEmpty(indent.getMemberId()) && ObjectUtil.isEmpty(payList) && ObjectUtil.isEmpty(details)) {
            BigDecimal memberDiscount = showMemberClient.selectMemberDiscount(indent.getMemberId()).getData();
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
            } else {
                indentPayMapper.updateMemberDiscount(indentId, PayBaseCode.MEMBER_DISCOUNT.code, -1, null);
            }
        } else {
            indentPayMapper.updateMemberDiscount(indentId, PayBaseCode.MEMBER_DISCOUNT.code, -1, null);
        }
    }

    /**
     * 计算订单商品买减活动
     *
     * @param indentId 订单ID
     */
    private void calcIndentMarket(String indentId) {
        Indent indent = indentMapper.selectByPrimaryKey(indentId);
        List<MarketInfo> marketList = marketInfoMapper.selectIndentMarket(indentId, indent.getStoreId());
        BigDecimal marketDiscount = BigDecimal.ZERO;
        List<String> marketIds = new LinkedList<>();
        for (MarketInfo marketInfo : marketList) {
            marketIds.add(marketInfo.getMarkId());
            int discountNum = (marketInfo.getQuantity() / marketInfo.getBuyQuantity())
                    * marketInfo.getDiscountQuantity();
            List<IndentDetail> detailList = indentDetailMapper.selectMarketDetail(indentId, marketInfo.getMarkId());
            for (IndentDetail detail : detailList) {
                BigDecimal diffPrice = BigDecimal.ZERO;
                BigDecimal salePrice = detail.getSalePrice();
                if (marketInfo.getDiscountType().equals(0)) {
                    diffPrice = NumberUtil.sub(salePrice, marketInfo.getAmount());
                } else if (marketInfo.getDiscountType().equals(1)) {
                    BigDecimal currSalePrice = NumberUtil.div(NumberUtil.mul(salePrice, marketInfo.getAmount()), 10);
                    diffPrice = NumberUtil.sub(salePrice, currSalePrice);
                }
                int quantity;
                if (detail.getQuantity() > discountNum) {
                    quantity = discountNum;
                    discountNum = 0;
                } else {
                    quantity = detail.getQuantity();
                    discountNum = discountNum - quantity;
                }
                marketDiscount = marketDiscount.add(diffPrice.multiply(new BigDecimal(quantity)));
                if (discountNum == 0) {
                    break;
                }
            }
        }
        if (marketDiscount.compareTo(BigDecimal.ZERO) <= 0) {
            return;
        }
        BigDecimal indentTotal = indentDetailMapper.selectIndentTotal(indentId);
        // 去除小数部分
        indent.setIndentTotal(NumberUtil.round(NumberUtil.sub(indentTotal, marketDiscount), 0, RoundingMode.DOWN));
        indent.setMarketDiscount(marketDiscount);
        indent.setMarketIds(org.apache.commons.lang3.StringUtils.join(marketIds.toArray(), ","));
        indentMapper.updateByPrimaryKey(indent);
    }

    @Override
    public void modifyDetail(DetailParam detailParam) {
        IndentDetail detail = indentDetailMapper.selectByPrimaryKey(detailParam.getMarkId());
        Indent indent = indentMapper.selectByPrimaryKey(detail.getIndentId());
        ShowAssert.checkTrue(detailParam.getQuantity() <= 0, StatusCode._4004);
        // 获取原有价格和数量
        BigDecimal salePrice = detail.getSalePrice();
        int quantity = detail.getQuantity();
        Commodity commodity = commodityMapper.selectByPrimaryKey(detailParam.getCommodityId());
        CommodityPrice commodityPrice = commodityPriceMapper.selectByIdOrDefault(detailParam.getPriceId());
        BigDecimal markupPrice = commodityItemMapper.sumItemPrice(commodity.getMarkId(), detailParam.getSpecsItems());
        BigDecimal price = commodityPrice.getSalePrice().add(markupPrice);
        detail.setCommodityType(commodity.getType());
        detail.setCommodityId(commodity.getMarkId());
        detail.setCommodityName(commodity.getName());
        detail.setPriceId(detailParam.getPriceId());
        detail.setSpecsItems(detailParam.getSpecsItems());
        detail.setCostPrice(price);
        detail.setPriceType(commodityPrice.getPriceType());
        detail.setIntegralPrice(commodityPrice.getIntegralPrice());
        BigDecimal discount = BigDecimal.ZERO;
        if (!StrUtil.isEmpty(detail.getDiscountId()) && commodityPrice.getPriceType() != 1) {
            DiscountInfo discountInfo = discountInfoMapper.selectByPrimaryKey(detail.getDiscountId());
            // 对商品进行折扣
            if (discountInfo.getType().equals(0)) {
                discount = discountInfo.getDiscount();
            } else if (discountInfo.getType().equals(1)) {
                BigDecimal currDiscount = NumberUtil.sub(1, NumberUtil.div(discountInfo.getDiscount(), 10));
                discount = NumberUtil.mul(price, currDiscount);
            }
        }
        detail.setSalePrice(commodityPrice.getPriceType() == 1 ? BigDecimal.ZERO : NumberUtil.sub(price, discount));
        detail.setQuantity(detailParam.getQuantity());
        detail.setModifyTime(DateUtil.date());
        // 计算价差 算出订单总金额
        BigDecimal diff = NumberUtil.mul(NumberUtil.sub(salePrice, detail.getSalePrice()), quantity);
        indent.setIndentTotal(NumberUtil.round(NumberUtil.sub(indent.getIndentTotal(), diff), 0, RoundingMode.DOWN));
        indentMapper.updateByPrimaryKeySelective(indent);
        indentDetailMapper.updateByPrimaryKeySelective(detail);
    }

    @Transactional
    @Override
    public List<String> deleteDetail(String detailId, String employeeId) {
        IndentDetail detail = indentDetailMapper.selectByPrimaryKey(detailId);
        BigDecimal salePrice = detail.getSalePrice();
        int status = detail.getStatus();
        int returnQuantity = 1;
        indentDetailMapper.deleteByPrimaryKey(detailId);
        detail.setModifier(employeeId);
        detail.setModifyTime(DateUtil.date());
        if (detail.getQuantity() > returnQuantity) {
            detail.setQuantity(detail.getQuantity() - returnQuantity);
            indentDetailMapper.insertSelective(detail);
            Snowflake snowflake = IdUtil.getSnowflake(1, 1);
            detail.setMarkId(snowflake.nextIdStr());
        }
        detail.setCostPrice(BigDecimal.ZERO);
        detail.setSalePrice(BigDecimal.ZERO);
        detail.setQuantity(returnQuantity);
        detail.setStatus(-1);
        indentDetailMapper.insertSelective(detail);
        Indent indent = indentMapper.selectByPrimaryKey(detail.getIndentId());
        // 减去商品价格*退菜数量 计算商品总金额
        BigDecimal discountTotal = NumberUtil.mul(salePrice, returnQuantity);
        indent.setIndentTotal(NumberUtil.round(NumberUtil.sub(indent.getIndentTotal(), discountTotal), 0, RoundingMode.DOWN));
        indentMapper.updateByPrimaryKeySelective(indent);
        addMemberIntegral(detail.getPriceType(), detail.getUserId(), detail.getIntegralPrice());
        List<String> logStamps = new LinkedList<>();
        // 系统确认后的订单退菜才会发送打印队列
        if (status == 1) {
            logStamps = printService.returnDish(indent.getMarkId(), employeeId, detail.getMarkId());
        }
        return logStamps;
    }

    /**
     * 退还积分
     *
     * @param userId
     * @param integralPrice
     */
    private void addMemberIntegral(int priceType, String userId, int integralPrice) {
        if (priceType != 1 || StrUtil.isEmpty(userId)) {
            return;
        }
        IntegralDetail detail = IntegralDetail.builder().userId(userId)
                .integralLimit(integralPrice).status(1).build();
        detail.setIntegralType(IntegralCode.EXCHANGE_WITHDRAW);
        ShowAssert.checkSuccess(showMemberClient.addIntegral(detail));
    }

    @Override
    public IndentBaseVo listDetailById(String indentId) {
        // 计算是否有买减活动
        calcIndentMarket(indentId);
        IndentBaseVo indent = indentMapper.selectBaseDetailById(indentId);
        List<DetailModel> detailList = new ArrayList<>();
        if (ObjectUtil.isNull(indent)) {
            indent = new IndentBaseVo();
        } else {
            detailList = indentDetailMapper.selectVoByIndent(indent.getIndentId());
        }
        indent.setDetailList(detailList);
        sender.calcIndentBaseTotal(indentId);
        return indent;
    }

    @Transactional
    @Override
    public void detailDiscount(DetailDiscountParam discountParam) {
        Indent indent = indentMapper.selectByPrimaryKey(discountParam.getIndentId());
        DiscountInfo discountInfo = discountInfoMapper.selectByPrimaryKey(discountParam.getDiscountId());
        ShowAssert.checkTrue(!StringUtils.isEmpty(discountInfo.getEmployIds())
                && !discountInfo.getEmployIds().contains(discountParam.getEmployeeId()), StatusCode._5026);
        List<IndentDetail> detailList = indentDetailMapper.selectByIndent(indent.getMarkId());
        detailList = detailList.stream().filter(detail -> !detail.getStatus().equals(-1) && discountParam.getDetailIds().contains(detail.getMarkId()))
                .collect(Collectors.toList());
        for (IndentDetail detail : detailList) {
            int quantity = detail.getQuantity();
            BigDecimal salePrice = detail.getSalePrice();
            BigDecimal costPrice = detail.getCostPrice();
            BigDecimal newSalePrice = BigDecimal.ZERO;
            // 对商品进行折扣
            if (discountInfo.getType().equals(0)) {
                newSalePrice = NumberUtil.sub(costPrice, discountInfo.getDiscount());
            } else if (discountInfo.getType().equals(1)) {
                newSalePrice = NumberUtil.mul(costPrice, NumberUtil.div(discountInfo.getDiscount(), 10));
            }
            detail.setSalePrice(newSalePrice);
            detail.setDiscountId(discountParam.getDiscountId());
            detail.setModifyTime(DateUtil.date());
            indentDetailMapper.updateByPrimaryKey(detail);
            BigDecimal discount = NumberUtil.sub(salePrice, newSalePrice);
            discount = NumberUtil.mul(discount, quantity);
            // 减去优惠并取整
            indent.setIndentTotal(NumberUtil.round(NumberUtil.sub(indent.getIndentTotal(), discount), 0, RoundingMode.DOWN));
        }
        indent.setModifier(discountParam.getEmployeeId());
        indent.setModifyTime(DateUtil.date());
        indentMapper.updateByPrimaryKeySelective(indent);
    }

    @Transactional
    @Override
    public List<String> bill(String indentId, String employeeId) {
        Indent indent = indentMapper.selectByPrimaryKey(indentId);
        BigDecimal payTotal = indentPayMapper.selectPayTotal(indentId);
        BigDecimal indentTotal = indent.getIndentTotal();
        ShowAssert.checkTrue(payTotal.compareTo(indentTotal) < 0, StatusCode._4041);
        indent.setIndentStatus(IndentStatus.BILL.code);
        Date date = DateUtil.date();
        indent.setBillBy(employeeId);
        indent.setBillTime(date);
        indent.setModifier(employeeId);
        indent.setModifyTime(date);
        indentMapper.updateByPrimaryKeySelective(indent);
        tableMapper.clearTable(indent.getTableId());
        // 发送打印队列
        List<String> logStamps = printService.previewOrBill(indentId, employeeId, PrinterCode.PT07.code);
        //会员消费送积分
        sender.memberIndentConsume(indent.getMarkId());
        //给用户推送订单评论信息
        sender.remarkInfo(indent.getMarkId());
        //判断用户是否可以领菜种
//        sender.activity(indent.getMarkId());
        return logStamps;
    }

    @Override
    public String cancelBill(String indentId, String employeeId) {
        Indent indent = indentMapper.selectByPrimaryKey(indentId);
        Table table = tableMapper.selectByPrimaryKey(indent.getTableId());
        ShowAssert.checkTrue(!TableStatus.FREEING.code.equals(table.getTableStatus()), StatusCode._4044);
        //修改订单金额
        BigDecimal indentTotal = indentDetailMapper.selectIndentTotal(indentId);
        indent.setIndentTotal(NumberUtil.round(indentTotal, 0, RoundingMode.DOWN));
        indent.setIndentStatus(IndentStatus.CREATE.code);
        table.setTempNum(indent.getTempNum());
        table.setTableStatus(TableStatus.EATING.code);
        table.setOpenTime(indent.getIndentTime());
        table.setManNum(indent.getManNum());
        tableMapper.updateByPrimaryKeySelective(table);
        indentMapper.updateByPrimaryKey(indent);
        sender.memberIndentConsumeRefund(indentId);
        return table.getMarkId();
    }

    @Override
    public PageGrid<IndentVo> page(PageParam<IndentPageParam> pageParam) {
        String sidx = "mark_id".equals(pageParam.getSidx()) ? "i.bill_time " : pageParam.getSidx();
        PageMethod.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        PageMethod.orderBy(sidx + " " + pageParam.getSort());
        PageInfo<IndentVo> pageInfo = new PageInfo<>(indentMapper.selectManageList(pageParam.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public void modifyIndentStatus(String indentId, String status) {
        String tableId = indentMapper.selectTableIdByIndentId(indentId);
        Integer exist = indentDetailMapper.selectExistNoCookTime(indentId);
        if (IndentStatus.PAYING.code.equals(status)) {
            ShowAssert.checkTrue(exist != null, StatusCode._5031);
            tableMapper.updateTableStatus(tableId, TableStatus.PAID.code);
        } else if (IndentStatus.CREATE.code.equals(status)) {
            if (ObjectUtil.isNotNull(exist)) {
                tableMapper.updateTableStatus(tableId, TableStatus.ORDERING.code);
            } else {
                tableMapper.updateTableStatus(tableId, TableStatus.EATING.code);
            }
        }
        indentMapper.updateIndentStatus(indentId, status);
    }

    @Override
    public Indent getInfo(String indentId) {
        // 计算是否有买减活动
        calcIndentMarket(indentId);
        return indentMapper.selectByPrimaryKey(indentId);
    }

    @Override
    public void bindMember(String indentId, String memberId) {
        Indent indent = indentMapper.selectByPrimaryKey(indentId);
        ShowAssert.checkTrue(!StrUtil.isEmpty(indent.getMemberId()), StatusCode._5029);
        indentMapper.bindMember(indentId, memberId);
    }


    @Override
    public List<Map<String, Object>> listIndentComm(String indentId) {
        return indentDetailMapper.selectCommByIndent(indentId);
    }

    @Override
    public Indent getInfoByTable(String tableId) {
        String tempNum = tableMapper.selectTempNumById(tableId);
        // 获取用户下单信息
        return indentMapper.selectByTable(tableId, tempNum);
    }

    @Override
    public BigDecimal getCostTotal(String indentId) {
        return indentDetailMapper.selectCostTotal(indentId);
    }

    @Override
    public List<String> listIndentUser(String indentId) {
        return indentDetailMapper.selectIndentUser(indentId);
    }

    @Override
    public IndentInfo selectById(String markId) {
        IndentInfo info = indentMapper.selectById(markId);
        if (StrUtil.isEmpty(info.getUserId())) {
            for (String user : info.getUserList()) {
                if (StrUtil.isNotEmpty(user)) {
                    info.setUserId(user);
                }
            }
        }
        return info;
    }

    @Override
    public UserIndent userIndent(String userId) {
        MemberAccount member = showMemberClient.getMemberInfoByUser(userId).getData();
        if (ObjectUtil.isEmpty(cartMapper.userCart(userId))) {
            return indentMapper.userIndent(userId, ObjectUtil.isEmpty(member) ? null : member.getMarkId());
        }
        return cartMapper.userCart(userId);
    }

    @Override
    public BigDecimal getMemberDiscountTotal(String indentId) {
        return indentDetailMapper.selectMemberDiscountTotal(indentId);
    }
}
