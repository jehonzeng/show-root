package com.szhengzhu.rabbitmq;

import cn.hutool.core.date.DateUtil;
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

    public void memberGrade(String memberId) {
        rabbitTemplate.convertAndSend(RabbitQueue.MEMBER_GRADE, memberId);
    }

//    public void activity(String accountId, String detailId) {
//        log.info("send:activity:{},{}", accountId, detailId);
//        Map<String, String> map = new HashMap<>(4);
//        map.put("accountId", accountId);
//        map.put("detailId", detailId);
//        rabbitTemplate.convertAndSend("activity", map);
//    }

//    public void test(String msg) {
//        rabbitTemplate.convertAndSend("fanoutExchange", "", msg);
//    }

    public void memberSign(String userId) {
        log.info("send:member-sign:{}", userId);
        Map<String, String> map = new HashMap<>(4);
        map.put("date", DateUtil.date().toString());
        map.put("userId", userId);
        rabbitTemplate.convertAndSend(RabbitQueue.MEMBER_SIGN, map);
    }

    public void integralExchange(String userId, String exchangeId) {
        log.info("send:member-exchange:{},{}", userId, exchangeId);
        Map<String, String> map = new HashMap<>(4);
        map.put("exchangeId", exchangeId);
        map.put("userId", userId);
        rabbitTemplate.convertAndSend(RabbitQueue.INTEGRAL_EXCHANGE, map);
    }

    public void memberLottery(String prizeId, String userId) {
        log.info("send:member-lottery:{},{}", prizeId, userId);
        Map<String, String> map = new HashMap<>(4);
        map.put("prizeId", prizeId);
        map.put("userId", userId);
        rabbitTemplate.convertAndSend(RabbitQueue.MEMBER_LOTTERY, map);
    }

    public void memberCombo(String markId, String userId, Integer quantity) {
        log.info("send:member-combo:{},{},{}", markId, userId, quantity);
        Map<String, String> map = new HashMap<>(4);
        map.put("markId", markId);
        map.put("userId", userId);
        map.put("quantity", quantity.toString());
        rabbitTemplate.convertAndSend(RabbitQueue.MEMBER_COMBO, map);
    }
}
