package com.szhengzhu.schedule;

import com.szhengzhu.mapper.IndentMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Component
public class IndentSchedule {
    
    @Resource
    private IndentMapper indentMapper;

    @Scheduled(cron = "0 0 3 * * ?")
    public void autoExpire() {
        indentMapper.updateExpireStatus();
    }
}
