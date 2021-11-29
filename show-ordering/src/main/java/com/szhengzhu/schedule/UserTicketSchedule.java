package com.szhengzhu.schedule;

import com.szhengzhu.bean.ordering.TicketExpire;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.mapper.UserTicketMapper;
import com.szhengzhu.rabbitmq.RabbitQueue;
import com.szhengzhu.rabbitmq.Sender;
import com.szhengzhu.redis.Redis;
import com.szhengzhu.redis.RedisKey;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 */
@Component
public class UserTicketSchedule {
    @Resource
    private Sender sender;

    @Resource
    private Redis redis;

    @Resource
    private UserTicketMapper userTicketMapper;

    /**
     * 优惠券过期
     */
    @Scheduled(cron = "0 0 9 ? * *")
    public void autoTicketExpire() {
        boolean result = redis.lock(RabbitQueue.TICKET_EXPIRE, 60);
        if (!result) {
            return;
        }
        String redisKey = RabbitQueue.TICKET_EXPIRE + "_" + RedisKey.SCHEDULE_INFO;
        boolean num = redis.hasKey(redisKey);
        if (!num) {
            redis.set(redisKey, 0, Contacts.SCHEDULE_EXPIRE);
            List<TicketExpire> userTickets = userTicketMapper.ticketExpire();
            if (ObjectUtils.isNotEmpty(userTickets)) {
                for (TicketExpire ticket : userTickets) {
                    sender.ticketExpire(ticket);
                }
            }
            redis.unlock(RabbitQueue.TICKET_EXPIRE);
        }
    }
}
