package com.szhengzhu.schedule;

import cn.hutool.core.date.DateUtil;
import com.szhengzhu.rabbitmq.Sender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Component
public class CouponSchedule {
    
    @Resource
    private Sender sender;
    
    @Scheduled(cron = "1 0 0 * * ?")
    public void clearCoupon() {
        System.out.println("清理过期优惠券");
        sender.clearCoupon(DateUtil.format(DateUtil.date(), "yyyy-MM-dd HH:mm:ss"));
    }
}
