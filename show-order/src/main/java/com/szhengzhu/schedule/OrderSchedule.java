package com.szhengzhu.schedule;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.szhengzhu.service.OrderService;

@Component
public class OrderSchedule {
    
    @Resource
    private OrderService orderService;

    @Scheduled(cron = "0 */1 * * * ?")
    public void cancelOrder() {
        
    }
}
