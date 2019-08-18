package com.szhengzhu.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.szhengzhu.rabbitmq.Sender;
import com.szhengzhu.util.TimeUtils;

@Component
public class CouponSchedule {
    
    @Autowired
    private Sender sender;
    
    @Scheduled(cron ="1 0 0 * * ?")
    public void clearCoupon() {
        sender.clearCoupon(TimeUtils.text());
    }
}
