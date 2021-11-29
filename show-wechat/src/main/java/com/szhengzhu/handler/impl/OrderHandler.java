package com.szhengzhu.handler.impl;

import cn.hutool.core.date.DateUtil;
import com.szhengzhu.annotation.OrderType;
import com.szhengzhu.client.ShowMemberClient;
import com.szhengzhu.client.ShowOrderClient;
import com.szhengzhu.bean.member.IntegralDetail;
import com.szhengzhu.bean.order.BackHistory;
import com.szhengzhu.bean.order.OrderInfo;
import com.szhengzhu.bean.wechat.vo.Judge;
import com.szhengzhu.bean.wechat.vo.OrderBase;
import com.szhengzhu.bean.wechat.vo.OrderDetail;
import com.szhengzhu.code.IntegralCode;
import com.szhengzhu.config.WechatConfig;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.code.OrderStatus;
import com.szhengzhu.core.Result;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.handler.AbstractOrder;
import com.szhengzhu.rabbitmq.Sender;
import com.szhengzhu.redis.Redis;
import com.szhengzhu.util.WechatUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author Jehon Zeng
 */
@Slf4j
@Component
@OrderType(Contacts.TYPE_OF_ORDER)
public class OrderHandler extends AbstractOrder {

    @Resource
    private Redis redis;

    @Resource
    private Sender sender;

    @Resource
    private ShowMemberClient showMemberClient;

    @Resource
    private ShowOrderClient showOrderClient;

    @Resource
    private WechatConfig wechatConfig;

    @Override
    public void orderBack(HttpServletResponse response, Map<String, String> payNotify, String payBackKey, int payType) throws Exception {
        String orderNo = payNotify.get("out_trade_no");
        if (WechatUtils.validateSignature(payNotify, wechatConfig.getKey())) {
            redis.set(payBackKey, 1, 60);
            orderPayBack(orderNo, payType);
            successResponse(response);
            log.info("回调成功、服务器验证成功：{}", orderNo);
        } else {
            Result<OrderInfo> orderResult = showOrderClient.getOrderByNo(orderNo);
            OrderInfo order = orderResult.getData();
            showOrderClient.addErrorBack(orderNo, order.getUserId());
            failResponse(response);
            log.info("回调成功、服务器验证失败：{}", orderNo);
        }
    }

    /**
     * 支付回调成功记录订单信息
     *
     * @param orderNo
     * @param payType
     * @date 2019年10月18日 下午4:21:51
     */
    private void orderPayBack(String orderNo, int payType) {
        showOrderClient.modifyOrderStatusByNo(orderNo, OrderStatus.PAID, null);
        BackHistory back = new BackHistory();
        back.setOrderNo(orderNo);
        back.setAddTime(DateUtil.date());
        back.setCid(back.getMarkId());
        back.setPayType(payType);
        back.setOrderType(1);
        showOrderClient.addOrderBack(back);
        // 修改商品真实库存
        sender.subTotalStock(orderNo);
        log.info("send message to manage");
        sender.sendManageMessage(orderNo);
    }

    @Override
    public void sendManageMessage(String orderNo) {
        Result<OrderInfo> orderResult = showOrderClient.getOrderByNo(orderNo);
        if (orderResult.isSuccess()) {
            OrderInfo order = orderResult.getData();
            sendMessageToManager(order.getMarkId(), orderNo, order.getOrderTime());
        }
//        Result<OrderDelivery> deliveryResult = showOrderClient.getDeliveryByOrder(order.getMarkId());
//        Result<List<Map<String, String>>> manageResult = showUserClient.listManager();
//        if (manageResult.isSuccess() && !manageResult.getData().isEmpty()) {
//            TemplateMessage message = WechatUtils.noticeOrderAction(orderNo, order.getOrderTime(),
//                    deliveryResult.getData());
//            for (Map<String, String> user : manageResult.getData()) {
//                if (StringUtils.isEmpty(user.get("wopenId"))) {
//                    break;
//                }
//                message.setTouser(user.get("wopenId"));
//                MessageAPI.messageTemplateSend(wechatConfig.refreshToken(), message);
//            }
//        }
    }

    @Override
    public OrderBase getOrderBase(String orderNo) {
        Result<OrderInfo> orderResult = showOrderClient.getOrderByNo(orderNo);
        ShowAssert.checkResult(orderResult);
        OrderInfo order = orderResult.getData();
        return OrderBase.builder()
                .orderId(order.getMarkId())
                .orderNo(order.getOrderNo()).payAmount(order.getPayAmount())
                .orderTime(order.getOrderTime()).userId(order.getUserId())
                .orderStatus(order.getOrderStatus()).type(Contacts.TYPE_OF_ORDER)
                .statusDesc(order.getStatusDesc()).build();
    }

    @Override
    public Result<OrderDetail> getOrderDetail(String orderNo, String userId) {
        return showOrderClient.getOrderDetail(orderNo, userId);
    }

    @Override
    public Result<List<Judge>> listOrderItemJudge(String orderNo, String userId) {
        return showOrderClient.listOrderItemJudge(orderNo, userId);
    }

    @Override
    public Result modifyStatusByNo(String orderNo, String orderStatus, String userId) {
        return showOrderClient.modifyOrderStatusByNo(orderNo, orderStatus, userId);
    }

    @Override
    public String orderJudge(String orderNo, String userId) {
        showOrderClient.modifyOrderStatusByNo(orderNo, OrderStatus.EVALUATED, userId);
        Result<OrderInfo> orderResult = showOrderClient.getOrderByNo(orderNo);
        if (!orderResult.isSuccess()) {
            return null;
        }
        OrderInfo orderInfo = orderResult.getData();
        IntegralDetail integral = new IntegralDetail();
        integral.setUserId(orderInfo.getUserId());
        integral.setIntegralLimit(orderInfo.getPayAmount().intValue());
        integral.setIntegralType(IntegralCode.JUDGE_GIVE);
        integral.setStatus(1);
        showMemberClient.addIntegral(integral);
        return orderInfo.getMarkId();
    }
}
