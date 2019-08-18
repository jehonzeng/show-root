package com.szhengzhu.rabbitmq;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.szhengzhu.service.UserTokenService;

/**
 * 监听消息
 * @author Administrator
 * @date 2019年2月22日
 */
@Component
public class Receiver {
    
    @Autowired
    private UserTokenService userTokenService;

    @RabbitHandler
    @RabbitListener(queues = "refresh-token", containerFactory = "containerFactory")
    public void refreshToken(String token, Message msg, Channel channel)
            throws IOException {
        onMessage(msg, channel);
        userTokenService.refreshToken(token);
    }

    private void onMessage(Message msg, Channel channel) throws IOException {
        channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
    }
}
