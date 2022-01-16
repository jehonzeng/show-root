package com.szhengzhu.schedule;

import com.szhengzhu.mapper.MatchChanceMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Component
public class MatchChanceSchedule {

    @Resource
    private MatchChanceMapper matchChanceMapper;

    @Scheduled(cron = "0 0 23 * * ?")
    public void autoCleanMatchChance() {
        matchChanceMapper.updateExpiredUserChance();
    }
}
