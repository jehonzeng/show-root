package com.szhengzhu.schedule;

import com.szhengzhu.bean.member.MatchInfo;
import com.szhengzhu.mapper.MatchChanceMapper;
import com.szhengzhu.mapper.MatchInfoMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author Administrator
 */
@Component
public class MatchChanceSchedule {

    @Resource
    private MatchChanceMapper matchChanceMapper;

    @Resource
    private MatchInfoMapper matchInfoMapper;

    @Scheduled(cron = "0 */1 * * * ?")
    public void autoCleanMatchChance() {
        List<MatchInfo> matchInfoList = matchInfoMapper.selectMatchTime();
        for (MatchInfo matchInfo : matchInfoList) {

        }
    }
}
