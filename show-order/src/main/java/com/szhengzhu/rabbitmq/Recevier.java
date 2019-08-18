package com.szhengzhu.rabbitmq;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.szhengzhu.service.UserCouponService;

@Component
public class Recevier {
    
    @Resource
    private UserCouponService userCouponService;
    
    @RabbitHandler
    @RabbitListener(queues = "coupon-clear", containerFactory = "containerFactory")
    public void clearCoupon(String text, Message msg, Channel channel)
            throws IOException {
        onMessage(msg, channel);
        System.out.println("clear-coupon:" + text);
        userCouponService.clearOverdueCoupon();
    }

    private void onMessage(Message msg, Channel channel) throws IOException {
        channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
    }
}
