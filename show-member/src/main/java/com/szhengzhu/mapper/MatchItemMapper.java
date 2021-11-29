package com.szhengzhu.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MatchItemMapper {

    @Delete("delete from t_match_item where match_id=#{matchId}")
    void deleteByMatch(@Param("matchId") String matchId);

    void insertBatch(List<String> list, @Param("matchId") String matchId);

}