package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.szhengzhu.bean.member.MatchInfo;
import com.szhengzhu.bean.member.MatchVote;
import com.szhengzhu.bean.member.VoteInfo;
import com.szhengzhu.bean.member.param.MatchTeamParam;
import com.szhengzhu.bean.member.param.MatchVoteParam;
import com.szhengzhu.bean.member.vo.MatchTeamVo;
import com.szhengzhu.bean.member.vo.StageResultVo;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.*;
import com.szhengzhu.service.MatchVoteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jehon
 */
@Service("matchVoteService")
public class MatchVoteServiceImpl implements MatchVoteService {

    @Resource
    private MatchChanceMapper matchChanceMapper;

    @Resource
    private MatchVoteMapper matchVoteMapper;

    @Resource
    private MatchResultMapper matchResultMapper;

    @Resource
    private MatchInfoMapper matchInfoMapper;

    @Resource
    private MatchTeamMapper matchTeamMapper;

    @Override
    public int getVoteChancesByUser(String userId) {
        Integer count = matchChanceMapper.selectUserCount(userId);
        return count == null ? 0 : count;
    }

    @Override
    public List<MatchTeamVo> listVoteTeam(String userId, String matchId) {
        List<MatchTeamVo> teamList = matchTeamMapper.selectVoteTeamByUserId(userId, matchId);
        for (MatchTeamVo teamVo : teamList) {
            List<StageResultVo> stageList = matchResultMapper.selectStageStatusByTeamId(matchId, teamVo.getMarkId(), userId);
            teamVo.setStageList(stageList);
        }
        return teamList;
    }

    @Override
    public void voteTeams(MatchVoteParam matchVoteParam) {
        MatchInfo matchInfo = matchInfoMapper.selectByPrimaryKey(matchVoteParam.getMatchId());
        boolean checkFlag = Boolean.FALSE.equals(matchInfo.getStatus()) || matchInfo.getEndTime().before(DateUtil.date());
        ShowAssert.checkTrue(checkFlag, StatusCode._5039);
        StageResultVo resultVo = matchResultMapper.selectLastByMatch(matchVoteParam.getMatchId());
        ShowAssert.checkTrue(resultVo.getLastTime() != null && DateUtil.date().isAfter(resultVo.getLastTime()), StatusCode._4061);
        int count = matchChanceMapper.selectUserCount(matchVoteParam.getUserId());
        int quantity = matchVoteParam.getTeams().stream().mapToInt(MatchTeamParam::getQuantity).sum();
        ShowAssert.checkTrue(count < quantity, StatusCode._4059);
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        int useQuantity = 0;
        for (MatchTeamParam teamParam : matchVoteParam.getTeams()) {
            String teamId = teamParam.getTeamId();
            List<StageResultVo> resultList = matchResultMapper.selectStageStatusByTeamId(matchVoteParam.getMatchId(), teamId, null);
            for (StageResultVo stageResultVo : resultList) {
                boolean isBeforeStart = DateUtil.date().isBefore(stageResultVo.getStageStart());
                boolean isBeforeLast = DateUtil.date().isAfter(stageResultVo.getStageStart()) &&
                        ObjectUtil.isNotNull(stageResultVo.getLastTime()) && DateUtil.date().isBefore(stageResultVo.getLastTime());
                if (isBeforeStart || isBeforeLast) {
                    useQuantity = useQuantity + teamParam.getQuantity();
                    MatchVote matchVote = MatchVote.builder().markId(snowflake.nextIdStr()).userId(matchVoteParam.getUserId()).teamId(teamId)
                            .quantity(teamParam.getQuantity()).createTime(DateUtil.date()).stageId(stageResultVo.getStageId()).build();
                    matchVoteMapper.insertSelective(matchVote);
                    break;
                }
            }
        }
        int vote = matchChanceMapper.selectUserCount(matchVoteParam.getUserId());
        ShowAssert.checkTrue(useQuantity == 0 || vote < useQuantity, StatusCode._5040);
        matchChanceMapper.updateUseCount(matchVoteParam.getUserId(), useQuantity);
    }

    @Override
    public List<String> listUserByMatch(String matchId, String teamId) {
        return matchResultMapper.selectUserByMatch(matchId, teamId);
    }

    @Override
    public List<VoteInfo> selectVoteInfo(String matchId, String teamId, String stageId) {
        return matchVoteMapper.selectVoteInfo(matchId, teamId, stageId);
    }
}
