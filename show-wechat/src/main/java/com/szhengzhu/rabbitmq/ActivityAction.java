package com.szhengzhu.rabbitmq;

import com.rabbitmq.client.Channel;
import com.szhengzhu.feign.ShowActivityClient;
import com.szhengzhu.bean.activity.ParticipantRelation;
import com.szhengzhu.core.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 消费消息
 *
 * @author Administrator
 * @date 2019年2月22日
 */
@Slf4j
@Component
public class ActivityAction {

    @Resource
    private ShowActivityClient showActivityClient;

    @RabbitHandler
    @RabbitListener(queues = RabbitQueue.RECEIVE_GIFT, containerFactory = "containerFactory")
    public void receivedGiftAction(ParticipantRelation base, Message msg, Channel channel)
            throws IOException {
        try {
            Result result = showActivityClient.autoReceiveGift(base.getActivityId(), base.getSonId(), 1);
            log.info("gift-receive(helper):{}", result.getCode());
            result = showActivityClient.autoReceiveGift(base.getActivityId(), base.getFatherId(), 0);
            log.info("gift-receive(initiator):{}", result.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            if (channel.isOpen()) {
                onMessage(msg, channel);
            }
        }
    }

    private void onMessage(Message msg, Channel channel) throws IOException {
        channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
    }
}
