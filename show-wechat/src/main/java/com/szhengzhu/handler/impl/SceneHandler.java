package com.szhengzhu.handler.impl;

import cn.hutool.core.date.DateUtil;
import com.szhengzhu.annotation.OrderType;
import com.szhengzhu.client.ShowActivityClient;
import com.szhengzhu.client.ShowOrderClient;
import com.szhengzhu.bean.activity.SceneOrder;
import com.szhengzhu.bean.order.BackHistory;
import com.szhengzhu.bean.wechat.vo.Judge;
import com.szhengzhu.bean.wechat.vo.OrderBase;
import com.szhengzhu.bean.wechat.vo.OrderDetail;
import com.szhengzhu.config.WechatConfig;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.code.OrderStatus;
import com.szhengzhu.core.Result;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.handler.AbstractOrder;
import com.szhengzhu.redis.Redis;
import com.szhengzhu.util.WechatUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Slf4j
@Component
@OrderType(Contacts.TYPE_OF_SCENE_ORDER)
public class SceneHandler extends AbstractOrder {

    @Resource
    private Redis redis;

    @Resource
    private ShowActivityClient showActivityClient;

    @Resource
    private ShowOrderClient showOrderClient;

    @Resource
    private WechatConfig wechatConfig;

    @Override
    public void orderBack(HttpServletResponse response, Map<String, String> payNotify, String payBackKey, int payType) throws Exception {
        String orderNo = payNotify.get("out_trade_no");
        if (WechatUtils.validateSignature(payNotify, wechatConfig.getKey())) {
            redis.set(payBackKey, 1, 60);
            sceneOrderPayBack(orderNo, payType);
            successResponse(response);
            log.info("回调成功、服务器验证成功：{}", orderNo);
        } else {
            Result<SceneOrder> orderResult = showActivityClient.getSceneOrderInfo(orderNo);
            SceneOrder order = orderResult.getData();
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
    private void sceneOrderPayBack(String orderNo, int payType) {
        showActivityClient.modifySceneOrderStatus(orderNo, OrderStatus.PAID);
        BackHistory back = new BackHistory();
        back.setOrderNo(orderNo);
        back.setAddTime(DateUtil.date());
        back.setPayType(payType);
        back.setOrderType(1);
        showOrderClient.addOrderBack(back);
    }

    @Override
    public void sendManageMessage(String orderNo) {
        return;
    }

    @Override
    public OrderBase getOrderBase(String orderNo) {
        Result<SceneOrder> orderResult = showActivityClient.getSceneOrderInfo(orderNo);
        ShowAssert.checkResult(orderResult);
        SceneOrder order = orderResult.getData();
        return OrderBase.builder()
                .orderId(order.getMarkId())
                .orderNo(order.getOrderNo()).payAmount(order.getPayAmount())
                .orderTime(order.getOrderTime()).userId(order.getUserId())
                .orderStatus(order.getOrderStatus())
                .statusDesc(order.getStatusDesc())
                .type(Contacts.TYPE_OF_SCENE_ORDER).build();
    }

    @Override
    public Result<?> modifyStatusByNo(String orderNo, String orderStatus, String userId) {
        return showActivityClient.modifySceneOrderStatus(orderNo, orderStatus);
    }

    @Override
    public Result<OrderDetail> getOrderDetail(String orderNo, String userId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Result<List<Judge>> listOrderItemJudge(String orderNo, String userId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String orderJudge(String orderNo, String userId) {
        // TODO Auto-generated method stub
        return null;
    }
}
