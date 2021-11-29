package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.member.MatchChance;
import com.szhengzhu.bean.member.MatchInfo;
import com.szhengzhu.bean.member.MatchTeam;
import com.szhengzhu.bean.member.vo.MatchTeamVo;
import com.szhengzhu.bean.member.vo.StageResultVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.*;
import com.szhengzhu.service.MatchTeamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jehon
 */
@Service("matchTeamService")
public class MatchTeamServiceImpl implements MatchTeamService {

    @Resource
    private MatchTeamMapper matchTeamMapper;

    @Resource
    private MatchResultMapper matchResultMapper;

    @Resource
    private MatchChanceMapper matchChanceMapper;

    @Resource
    private MatchInfoMapper matchInfoMapper;

    @Resource
    private MemberAccountMapper memberAccountMapper;

    @Override
    public PageGrid<MatchTeam> page(PageParam<MatchTeam> param) {
        PageMethod.startPage(param.getPageIndex(), param.getPageSize());
        PageMethod.orderBy("create_time " + param.getSort());
        PageInfo<MatchTeam> pageInfo = new PageInfo<>(
                matchTeamMapper.selectByExampleSelective(param.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public MatchTeam getInfo(String markId) {
        return matchTeamMapper.selectByPrimaryKey(markId);
    }

    @Override
    public void add(MatchTeam matchTeam) {
        int count = matchTeamMapper.selectCountByName(matchTeam.getTeamName(), matchTeam.getMarkId());
        ShowAssert.checkTrue(count > 0, StatusCode._4004);
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        matchTeam.setMarkId(snowflake.nextIdStr());
        matchTeam.setCreateTime(DateUtil.date());
        matchTeam.setStatus(true);
        matchTeamMapper.insertSelective(matchTeam);
    }

    @Override
    public void modify(MatchTeam matchTeam) {
        int count = matchTeamMapper.selectCountByName(matchTeam.getTeamName(), matchTeam.getMarkId());
        ShowAssert.checkTrue(count > 0, StatusCode._4004);
        matchTeam.setModifyTime(DateUtil.date());
        matchTeamMapper.updateByPrimaryKeySelective(matchTeam);
    }

    @Override
    public List<MatchTeam> list() {
        return matchTeamMapper.selectList();
    }

    @Override
    public List<MatchTeamVo> listMatchTeamByMatch(String matchId) {
        List<MatchTeamVo> teamVoList = matchTeamMapper.selectBaseListByMatch(matchId);
        for (MatchTeamVo teamVo : teamVoList) {
            List<StageResultVo> stageList = matchResultMapper.selectStageStatusByTeamId(matchId, teamVo.getMarkId(), null);
            teamVo.setStageList(stageList);
        }
        return teamVoList;
    }

    @Override
    public List<MatchTeamVo> listMatchVoteTeamList(String userId, String matchId) {
        getChance(userId, matchId);
        return matchTeamMapper.selectBaseListByMatch(matchId);
    }

    public void getChance(String userId, String matchId) {
        MatchInfo matchInfo = matchInfoMapper.selectMatchInfo(matchId);
        MatchChance matchChance = matchChanceMapper.selectByPrimaryKey(userId);
        if (ObjectUtil.isNotEmpty(memberAccountMapper.selectByUserId(userId)) && matchInfo.getGiveChance().equals(1)) {
            if (ObjectUtil.isEmpty(matchChance)) {
                matchChanceMapper.insertSelective(MatchChance.builder().userId(userId).matchId(matchId).totalCount(1).usedCount(0).createTime(DateUtil.date()).build());
            } else if (StrUtil.isEmpty(matchChance.getMatchId())) {
                matchChanceMapper.updateByPrimaryKeySelective(MatchChance.builder().userId(userId).matchId(matchId).totalCount(matchChance.getTotalCount() + 1).modifyTime(DateUtil.date()).build());
            }
        }
    }
}
