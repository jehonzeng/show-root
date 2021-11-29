package com.szhengzhu.mapper;

import com.szhengzhu.bean.activity.ActivityRule;

/**
 * @author Administrator
 */
public interface ActivityRuleMapper {

    int deleteByPrimaryKey(String markId);

    int insert(ActivityRule record);

    int insertSelective(ActivityRule record);

    ActivityRule selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(ActivityRule record);

    int updateByPrimaryKey(ActivityRule record);
}