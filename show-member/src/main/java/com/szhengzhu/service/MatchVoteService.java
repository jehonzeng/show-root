package com.szhengzhu.service;

import com.szhengzhu.bean.member.VoteInfo;
import com.szhengzhu.bean.member.param.MatchVoteParam;
import com.szhengzhu.bean.member.vo.MatchTeamVo;

import java.util.List;

/**
 * @author jehon
 */
public interface MatchVoteService {

    /**
     * @param userId
     * @return
     */
    int getVoteChancesByUser(String userId);

    /**
     * 获取已投队伍信息列表
     *
     * @param userId
     * @param matchId
     * @return
     */
    List<MatchTeamVo> listVoteTeam(String userId, String matchId);

    /**
     * 用户投票
     *
     * @param matchVoteParam
     */
    void voteTeams(MatchVoteParam matchVoteParam);

    /**
     * 获取
     *
     * @param matchId
     * @param teamId
     * @return
     */
    List<String> listUserByMatch(String matchId, String teamId);

    /**
     * 获取该阶段队伍的投票情况
     *
     * @param teamId
     * @param stageId
     * @return
     */
    List<VoteInfo> selectVoteInfo(String matchId, String teamId, String stageId);
}
