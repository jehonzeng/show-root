package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.szhengzhu.bean.base.ActionItem;

public interface ActionItemMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(ActionItem record);

    int insertSelective(ActionItem record);

    ActionItem selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(ActionItem record);

    int updateByPrimaryKey(ActionItem record);
    
    List<ActionItem> selectByCode(@Param("code") String code);
    
    List<ActionItem> selectByExampleSelective(ActionItem item);
}