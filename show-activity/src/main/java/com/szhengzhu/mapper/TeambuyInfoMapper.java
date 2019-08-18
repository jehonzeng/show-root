package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.szhengzhu.bean.activity.TeambuyInfo;

public interface TeambuyInfoMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(TeambuyInfo record);

    int insertSelective(TeambuyInfo record);

    TeambuyInfo selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(TeambuyInfo record);

    int updateByPrimaryKey(TeambuyInfo record);
    
    List<TeambuyInfo> selectByExampleSelective(TeambuyInfo teambuyInfo);
    
    TeambuyInfo selectByKeyAndSpec(@Param("markId") String markId, @Param("specIds") String specIds);
}