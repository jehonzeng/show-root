package com.szhengzhu.mapper;

import java.util.List;

import com.szhengzhu.bean.base.ActionInfo;

public interface ActionInfoMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(ActionInfo record);

    int insertSelective(ActionInfo record);

    ActionInfo selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(ActionInfo record);

    int updateByPrimaryKey(ActionInfo record);
    
    List<ActionInfo> selectByExampleSelective(ActionInfo actionInfo);
}