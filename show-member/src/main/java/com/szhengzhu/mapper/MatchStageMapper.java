package com.szhengzhu.mapper;

import com.szhengzhu.bean.member.MatchStage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MatchStageMapper {

    int deleteByPrimaryKey(String markId);

    int insert(MatchStage record);

    int insertSelective(MatchStage record);

    MatchStage selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(MatchStage record);

    int updateByPrimaryKey(MatchStage record);

    List<MatchStage> selectByMatch(@Param("matchId") String matchId);

    @Select("select * from t_match_stage where match_id=#{matchId} order by end_time desc limit 1")
    String selectLastStageId(@Param("matchId") String matchId);
}