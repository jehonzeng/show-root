package com.szhengzhu.rabbitmq;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.stereotype.Component;

/**
 * 信息生产
 * 
 * @author Administrator
 * @date 2019年2月21日
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
    
    public void test(String id) {
        rabbitTemplate.convertAndSend("test-send", id);
    }
    
    public void modifyProductStock(Map<String, Object> mqParam) {
        rabbitTemplate.convertAndSend("product-stock", mqParam);
    }
}
