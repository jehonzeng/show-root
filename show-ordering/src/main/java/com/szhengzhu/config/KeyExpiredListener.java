package com.szhengzhu.config;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.szhengzhu.bean.ordering.Booking;
import com.szhengzhu.bean.ordering.param.BookingParam;
import com.szhengzhu.mapper.BookingMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import javax.annotation.Resource;

@Slf4j
public class KeyExpiredListener extends KeyExpirationEventMessageListener {

    @Resource
    private BookingMapper bookingMapper;

    public KeyExpiredListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String redisKey = message.toString();
        if (redisKey.split("_")[0].equals("booking")) {
            String bookingId = redisKey.split("_")[1];
            log.info("过期key：{},过期时间：{}", redisKey, DateUtil.format(DateUtil.date(), "yyyy-MM-dd HH:mm:ss"));
            if (ObjectUtil.isNotEmpty(bookingMapper.selectInfo(BookingParam.builder().markId(bookingId).status(1).build()))) {
                //修改预订状态
                bookingMapper.modifyStatus(Booking.builder().markId(bookingId).status(0).modifyTime(DateUtil.date()).build());
            }
        }
    }
}


