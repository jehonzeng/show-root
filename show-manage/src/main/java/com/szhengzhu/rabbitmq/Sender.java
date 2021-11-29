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
 * 信息生产
 *
 * @author Administrator
 * @date 2019年2月21日
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
     * 清理过期券
     *
     * @param text
     * @date 2019年8月28日
     */
    public void clearCoupon(String text) {
        rabbitTemplate.convertAndSend(RabbitQueue.CLEAR_COUPON, text);
    }

    /**
     * 订单确认，给用户提醒
     *
     * @date 2019年8月26日 下午4:41:14
     * @param map
     */
    public void sendOrderConfirmMsg(Map<String, String> map) {
        rabbitTemplate.convertAndSend(RabbitQueue.SEND_ORDER_CONFIRM_MSG, map);
    }


    /**
     * 订单配送，给用户提醒
     *
     * @date 2019年8月26日 下午4:41:49
     * @param map
     */
    public void sendOrderDeliveryMsg(Map<String, String> map) {
        rabbitTemplate.convertAndSend(RabbitQueue.SEND_ORDER_DELIVERY_MSG, map);
    }

    /**
     * 给用户退款
     *
     * @date 2019年9月4日 上午10:29:10
     * @param map
     */
    public void orderRefund(Map<String, String> map) {
        rabbitTemplate.convertAndSend(RabbitQueue.ORDER_REFUND, map);
    }

    /**
     * 给用户退款
     *
     * @date 2019年9月4日 上午10:29:10
     * @param orderNo
     */
    public void sceneOrderRefund(String orderNo) {
        rabbitTemplate.convertAndSend(RabbitQueue.SCENE_ORDER_REFUND, orderNo);
    }

    /**
     * 通知用户异常
     */
    public void contactUser(String orderId, String content, String reason) {
        log.info("send:contact-user:{}{}{}", orderId, content, reason);
        Map<String, String> map = new HashMap<>(4);
        map.put("orderId", orderId);
        map.put("content", content);
        map.put("reason", reason);
        rabbitTemplate.convertAndSend(RabbitQueue.CONTACT_USER, map);
    }
}
