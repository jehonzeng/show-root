package com.szhengzhu.rabbitmq;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

/**
 * 信息消费者
 * 
 * @author Administrator
 * @date 2019年2月21日
 */
@Component
public class Recevier {

    @RabbitHandler
    @RabbitListener(queues = "test-send", containerFactory = "containerFactory")
    public void test(String id,Message msg, Channel channel) throws IOException {
        onMessage(msg, channel);
        System.out.println("test-send:");
    }

    private void onMessage(Message msg, Channel channel) throws IOException {
        channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
        
    }

}
