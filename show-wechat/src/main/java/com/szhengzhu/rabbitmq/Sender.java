package com.szhengzhu.rabbitmq;

import javax.annotation.PostConstruct;

import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.stereotype.Component;

/**
 * 生产消息
 * 
 * @author Administrator
 * @date 2019年2月22日
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

    // 指定队列接收消息
    public void refreshToken(String token) {
        rabbitTemplate.convertAndSend("refresh-token", token);
    }
}
