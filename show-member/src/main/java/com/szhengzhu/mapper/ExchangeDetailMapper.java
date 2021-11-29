package com.szhengzhu.mapper;

import com.szhengzhu.bean.member.ExchangeDetail;

public interface ExchangeDetailMapper {

    int deleteByPrimaryKey(String markId);

    int insert(ExchangeDetail record);

    int insertSelective(ExchangeDetail record);

    ExchangeDetail selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(ExchangeDetail record);

    int updateByPrimaryKey(ExchangeDetail record);
}