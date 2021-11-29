package com.szhengzhu.mapper;

import com.szhengzhu.bean.ordering.StoreEmployee;

public interface StoreEmployeeMapper {
    
    int deleteByPrimary(StoreEmployee key);

    int insert(StoreEmployee record);

    int insertSelective(StoreEmployee record);
}