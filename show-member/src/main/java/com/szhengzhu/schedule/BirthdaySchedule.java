package com.szhengzhu.schedule;

import cn.hutool.core.util.ObjectUtil;
import com.szhengzhu.bean.member.MemberActivity;
import com.szhengzhu.bean.member.ReceiveTicket;
import com.szhengzhu.code.MemberActivityCode;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.mapper.MemberAccountMapper;
import com.szhengzhu.mapper.MemberActivityMapper;
import com.szhengzhu.mapper.ReceiveTicketMapper;
import com.szhengzhu.rabbitmq.RabbitQueue;
import com.szhengzhu.rabbitmq.Sender;
import com.szhengzhu.redis.Redis;
import com.szhengzhu.redis.RedisKey;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Component
public class BirthdaySchedule {

    @Resource
    private Redis redis;

    @Resource
    private Sender sender;

    @Resource
    private MemberAccountMapper memberAccountMapper;

    @Resource
    private MemberActivityMapper memberActivityMapper;

    @Resource
    private ReceiveTicketMapper receiveTicketMapper;

    /**
     * 会员生日推送
     */
    @Scheduled(cron = "0 30 9 ? * *")
    public void autoTicketExpire() {
        boolean result = redis.lock(RabbitQueue.BIRTHDAY_TICKET, 60);
        if (!result) {
            return;
        }
        String redisKey = RabbitQueue.BIRTHDAY_TICKET + "_" + RedisKey.SCHEDULE_INFO;
        boolean num = redis.hasKey(redisKey);
        if (!num) {
            redis.set(redisKey, 0, Contacts.SCHEDULE_EXPIRE);
            MemberActivity activity = memberActivityMapper.queryById(MemberActivityCode.BIRTHDAY_TICKET.code);
            if (ObjectUtil.isNotNull(activity) && activity.getStatus() == 1 && ObjectUtil.isNotEmpty(activity.getDays())) {
                List<ReceiveTicket> tickets = receiveTicketMapper.queryById(activity.getMarkId());
                if (ObjectUtil.isNotNull(tickets)) {
                    List<Map<String, String>> memberMap = memberAccountMapper.birthdayTicket(activity.getDays());
                    for (Map<String, String> stringMap : memberMap) {
                        for (ReceiveTicket ticket : tickets) {
                            Map<String, String> map = new HashMap<>();
                            map.put("userId", stringMap.get("userId"));
                            map.put("templateId", ticket.getTemplateId());
                            map.put("quantity", ticket.getQuantity().toString());
                            sender.memberReceiveTicket(map);
                        }
                        sender.birthdayTicket(stringMap);
                    }
                }
            }
            redis.unlock(RabbitQueue.BIRTHDAY_TICKET);
        }
    }
}
