package com.szhengzhu.mapper;

import com.szhengzhu.bean.member.MatchPay;

public interface MatchPayMapper {

    int deleteByPrimaryKey(String markId);

    int insert(MatchPay record);

    int insertSelective(MatchPay record);

    MatchPay selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(MatchPay record);

    int updateByPrimaryKey(MatchPay record);
}