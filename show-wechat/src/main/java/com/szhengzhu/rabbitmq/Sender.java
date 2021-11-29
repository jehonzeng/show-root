package com.szhengzhu.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 生产消息
 * 
 * @author Jehon Zeng
 */
@Slf4j
@Component
public class Sender {

    @Autowired
    private RabbitMessagingTemplate rabbitTemplate;

    @Autowired
    private MappingJackson2MessageConverter converter;

    @PostConstruct
    public void init() {
        rabbitTemplate.setMessageConverter(converter);
    }

    /**
     * 指定队列接收消息
     * 
     * @param token
     */
    public void refreshToken(String token) {
        rabbitTemplate.convertAndSend(RabbitQueue.REFRESH_TOKEN, token);
    }

    /**
     * 修改公众号用户状态
     * 
     * @param wopenId
     * @param wechatStatus
     */
    public void modifyWechatStatus(String wopenId, int wechatStatus) {
        Map<String, Object> map = new HashMap<>(4);
        map.put("wopenId", wopenId);
        map.put("wechatStatus", wechatStatus);
        rabbitTemplate.convertAndSend(RabbitQueue.MODIFY_WECHAT_STATUS, map);
    }

    /**
     * 支付成功的订单退款后加库存
     * 
     * @date 2019年8月8日 上午10:06:11
     */
    public void addTotalStock(String orderNo) {
        log.info("send:add-total-stock:{}", orderNo);
        rabbitTemplate.convertAndSend(RabbitQueue.ADD_TOTAL_STOCK, orderNo);
    }

    /**
     * 取消订单恢复库存
     * 
     * @date 2019年8月6日 下午3:37:39
     * @param orderNo
     */
    public void addCurrentStock(String orderNo) {
        log.info("send:add-current-stock:{}", orderNo);
        rabbitTemplate.convertAndSend(RabbitQueue.ADD_CURRENT_STOCK, orderNo);
    }

    /**
     * 用户下单成功修改商品真实库存
     * 
     * @date 2019年8月8日 上午10:00:09
     * @param orderNo
     */
    public void subTotalStock(String orderNo) {
        rabbitTemplate.convertAndSend(RabbitQueue.SUB_TOTAL_STOCK, orderNo);
    }

    /**
     * 用户下单支付成功，系统自动给管理员发送下单信息
     * 
     * @date 2019年8月26日 下午2:52:57
     * @param orderNo
     */
    public void sendManageMessage(String orderNo) {
        log.info("send:send-manage-message:{}", orderNo);
        rabbitTemplate.convertAndSend(RabbitQueue.SEND_MANAGE_MESSAGE, orderNo);
    }
    
    public void addSceneGoodsStock(String orderId) {
        rabbitTemplate.convertAndSend(RabbitQueue.ADD_SCENE_GOODS_STOCK, orderId);
    }
    
//    public void addMemberAccount(String userId) {
//        System.out.println("新用户注册会员：" + userId);
//        rabbitTemplate.convertAndSend("add-member-account", userId);
//    }
}
