package com.szhengzhu.rabbitmq;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 生产消息
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

    public void memberConsume(String userId, BigDecimal amount) {
        log.info("send:member-consume:{},{}", userId, amount);
        Map<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("amount", amount.toString());
        rabbitTemplate.convertAndSend(RabbitQueue.MEMBER_CONSUME, map);
    }

    public void memberConsumeRefund(String indentId, String userId, BigDecimal amount) {
        log.info("send:member-consume-refund:{},{}", userId, amount);
        Map<String, String> map = new HashMap<>(4);
        if (StrUtil.isNotEmpty(indentId)) {
            map.put("indentId", indentId);
            rabbitTemplate.convertAndSend(RabbitQueue.MEMBER_INDENT_CONSUME_REFUND, map);
        } else {
            map.put("userId", userId);
            map.put("amount", amount.toString());
            rabbitTemplate.convertAndSend(RabbitQueue.MEMBER_CONSUME_REFUND, map);
        }
    }

    public void memberSign(String userId, String date) {
        log.info("send:member-sign:{},{}", userId, date);
        Map<String, String> map = new HashMap<>(4);
        map.put("date", date);
        map.put("userId", userId);
        rabbitTemplate.convertAndSend(RabbitQueue.MEMBER_SIGN, map);
    }

    public void memberLottery(String prizeId, String userId) {
        log.info("send:member-lottery:{},{}", prizeId, userId);
        Map<String, String> map = new HashMap<>(4);
        map.put("prizeId", prizeId);
        map.put("userId", userId);
        rabbitTemplate.convertAndSend(RabbitQueue.MEMBER_LOTTERY, map);
    }

    /**
     * 会员折扣
     */
    public void memberDiscount(String indentId, String memberId) {
        log.info("send:member-discount:{},{}", indentId, memberId);
        Map<String, String> map = new HashMap<>(4);
        map.put("indentId", indentId);
        map.put("memberId", memberId);
        rabbitTemplate.convertAndSend(RabbitQueue.MEMBER_DISCOUNT, map);
    }

    public void reservation(String comboId) {
        log.info("send:reservation-notify:{}", comboId);
        rabbitTemplate.convertAndSend(RabbitQueue.RESERVATION_NOTIFY, comboId);
    }
}
