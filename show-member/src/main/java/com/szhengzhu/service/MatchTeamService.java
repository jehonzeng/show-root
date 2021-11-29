package com.szhengzhu.service;

import com.szhengzhu.bean.member.MatchTeam;
import com.szhengzhu.bean.member.vo.MatchTeamVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

/**
 * @author jehon
 */
public interface MatchTeamService {

    /**
     * 获取竞赛队伍分页列表
     *
     * @param param
     * @return
     */
    PageGrid<MatchTeam> page(PageParam<MatchTeam> param);

    /**
     * 获取队伍信息
     *
     * @param markId
     * @return
     */
    MatchTeam getInfo(String markId);

    /**
     * 添加竞赛队伍
     *
     * @param matchTeam
     */
    void add(MatchTeam matchTeam);

    /**
     * 修改竞赛队伍
     *
     * @param matchTeam
     */
    void modify(MatchTeam matchTeam);

    /**
     * 获取队伍列表
     *
     * @return
     */
    List<MatchTeam> list();

    /**
     * 获取赛事下的队伍集合
     *
     * @param matchId
     * @return
     */
    List<MatchTeamVo> listMatchTeamByMatch(String matchId);

    /**
     * 获取竞赛队伍列表
     *
     * @param matchId
     * @return
     */
    List<MatchTeamVo> listMatchVoteTeamList(String userId,String matchId);
}
