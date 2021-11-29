package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.szhengzhu.bean.member.MatchResult;
import com.szhengzhu.bean.member.MatchStage;
import com.szhengzhu.mapper.MatchResultMapper;
import com.szhengzhu.mapper.MatchStageMapper;
import com.szhengzhu.rabbitmq.Sender;
import com.szhengzhu.service.MatchStageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jehon
 */
@Service("matchStageService")
public class MatchStageServiceImpl implements MatchStageService {

    @Resource
    private Sender sender;
    
    @Resource
    private MatchStageMapper matchStageMapper;

    @Resource
    private MatchResultMapper matchResultMapper;

    @Override
    public List<MatchStage> listByMatch(String matchId) {
        return matchStageMapper.selectByMatch(matchId);
    }

    @Override
    public void add(MatchStage matchStage) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        matchStage.setMarkId(snowflake.nextIdStr());
        matchStage.setCreateTime(DateUtil.date());
        matchStage.setStatus(true);
        matchStageMapper.insertSelective(matchStage);
    }

    @Override
    public void modify(MatchStage matchStage) {
        matchStage.setModifyTime(DateUtil.date());
        matchStageMapper.updateByPrimaryKeySelective(matchStage);
    }

    @Override
    public void addStageTeam(MatchResult matchResult) {
        MatchResult result = matchResultMapper.selectByTeam(matchResult.getStageId(), matchResult.getTeamId());
        if (ObjectUtil.isNull(result)) {
            Snowflake snowflake = IdUtil.getSnowflake(1, 1);
            matchResult.setMarkId(snowflake.nextIdStr());
            matchResultMapper.insertSelective(matchResult);
            return;
        }
        result.setLastTime(matchResult.getLastTime());
        result.setTeamStatus(matchResult.getTeamStatus());
        matchResultMapper.updateByPrimaryKeySelective(result);
        if (matchResult.getTeamStatus() != 0) {
            MatchStage matchStage = matchStageMapper.selectByPrimaryKey(matchResult.getStageId());
            String stageId = matchStageMapper.selectLastStageId(matchResult.getStageId());
            sender.sendTeamStatus(matchStage.getMatchId(), matchResult.getTeamId(), matchResult.getTeamStatus(), matchResult.getStageId().equals(stageId));
        }
    }

    @Override
    public void openPrize(String stageId) {

    }
}
