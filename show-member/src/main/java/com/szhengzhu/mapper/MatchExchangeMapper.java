package com.szhengzhu.mapper;

import com.szhengzhu.bean.member.MatchExchange;
import org.apache.ibatis.annotations.Param;

public interface MatchExchangeMapper {
    int deleteByPrimaryKey(String markId);

    int insert(MatchExchange record);

    int insertSelective(MatchExchange record);

    MatchExchange selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(MatchExchange record);

    int updateByPrimaryKey(MatchExchange record);

    MatchExchange selectUserExchangeByMatch(@Param("matchId") String matchId, @Param("userId") String userId);
}