package com.szhengzhu.mapper;

import com.szhengzhu.bean.member.MatchReceive;

public interface MatchReceiveMapper {

    int deleteByPrimaryKey(String markId);

    int insert(MatchReceive record);

    int insertSelective(MatchReceive record);

    MatchReceive selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(MatchReceive record);

    int updateByPrimaryKey(MatchReceive record);
}