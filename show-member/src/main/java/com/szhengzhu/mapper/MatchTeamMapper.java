package com.szhengzhu.mapper;

import com.szhengzhu.bean.member.MatchTeam;
import com.szhengzhu.bean.member.vo.MatchTeamVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MatchTeamMapper {

    int deleteByPrimaryKey(String markId);

    int insert(MatchTeam record);

    int insertSelective(MatchTeam record);

    MatchTeam selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(MatchTeam record);

    int updateByPrimaryKey(MatchTeam record);

    List<MatchTeam> selectByExampleSelective(MatchTeam record);

    @Select("select count(1) from t_match_team where team_name = #{teamName} and mark_id <> #{markId}")
    int selectCountByName(@Param("teamName") String teamName, @Param("markId") String markId);

    List<MatchTeam> selectList();

    List<MatchTeamVo> selectBaseListByMatch(@Param("matchId") String matchId);

    List<MatchTeamVo> selectVoteTeamByUserId(@Param("userId") String userId, @Param("matchId") String matchId);
}