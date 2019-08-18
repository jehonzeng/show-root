package com.szhengzhu.mapper;

import java.util.List;

import com.szhengzhu.bean.activity.SeckillInfo;

public interface SeckillInfoMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(SeckillInfo record);

    int insertSelective(SeckillInfo record);

    SeckillInfo selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(SeckillInfo record);

    int updateByPrimaryKey(SeckillInfo record);
    
    List<SeckillInfo> selectByExampleSelective(SeckillInfo seckillInfo);
}