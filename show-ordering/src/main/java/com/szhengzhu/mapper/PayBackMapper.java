package com.szhengzhu.mapper;

import com.szhengzhu.bean.ordering.PayBack;

public interface PayBackMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(PayBack record);

    int insertSelective(PayBack record);

    PayBack selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(PayBack record);

    int updateByPrimaryKey(PayBack record);
}