package com.szhengzhu.mapper;

import com.szhengzhu.bean.member.MatchPrize;

import java.util.List;

public interface MatchPrizeMapper {

    int deleteByPrimaryKey(String markId);

    int insert(MatchPrize record);

    int insertSelective(MatchPrize record);

    MatchPrize selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(MatchPrize record);

    int updateByPrimaryKey(MatchPrize record);

    List<MatchPrize> selectByExampleSelective(MatchPrize record);
}