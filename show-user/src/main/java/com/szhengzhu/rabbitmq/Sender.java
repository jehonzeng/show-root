package com.szhengzhu.rabbitmq;

import javax.annotation.PostConstruct;

import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.stereotype.Component;

/**
 * 生产消息
 * 
 * @author Jehon Zeng
 */
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

//    public void addMemberAccount(String userId) {
//        System.out.println("新用户注册会员：" + userId);
//        rabbitTemplate.convertAndSend("add-member-account", userId);
//    }
}
