package com.szhengzhu.mapper;

import com.szhengzhu.bean.member.MatchResult;
import com.szhengzhu.bean.member.vo.StageResultVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MatchResultMapper {

    int deleteByPrimaryKey(String markId);

    int insert(MatchResult record);

    int insertSelective(MatchResult record);

    MatchResult selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(MatchResult record);

    int updateByPrimaryKey(MatchResult record);

    void insertBatch(List<MatchResult> list);

    MatchResult selectByTeam(@Param("stageId") String stageId, @Param("teamId") String teamId);

//    List<StageResultVo> selectStageStatusByTeamId(@Param("matchId") String matchId, @Param("teamId") String teamId);

    @Select("SELECT user_id from t_match_vote v LEFT JOIN t_match_stage s on s.mark_id=v.stage_id where s.match_id=#{matchId} and v.team_id=#{teamId}")
    List<String> selectUserByMatch(@Param("matchId") String matchId, @Param("teamId") String teamId);

    List<StageResultVo> selectStageStatusByTeamId(@Param("matchId") String matchId, @Param("teamId") String teamId, @Param("userId") String userId);

    StageResultVo selectLastByMatch(@Param("matchId") String matchId);
}
