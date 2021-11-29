package com.szhengzhu.service;

import com.szhengzhu.bean.member.MatchResult;
import com.szhengzhu.bean.member.MatchStage;

import java.util.List;

/**
 * @author jehon
 */
public interface MatchStageService {

    /**
     * 获取竞赛赛程列表
     *
     * @param matchId
     * @return
     */
    List<MatchStage> listByMatch(String matchId);

    /**
     * 添加竞赛赛程
     *
     * @param matchStage
     */
    void add(MatchStage matchStage);

    /**
     * 修改竞赛赛程
     *
     * @param matchStage
     */
    void modify(MatchStage matchStage);

    /**
     * 添加竞赛赛程参与的队伍
     *
     * @param matchResult
     */
    void addStageTeam(MatchResult matchResult);

    /**
     * 赛程开奖
     *
     * @param stageId
     */
    void openPrize(String stageId);
}
