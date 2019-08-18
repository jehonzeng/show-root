package com.szhengzhu.mapper;

import java.util.List;

import com.szhengzhu.bean.activity.LinkItem;

public interface LinkItemMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(LinkItem record);

    int insertSelective(LinkItem record);

    LinkItem selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(LinkItem record);

    int updateByPrimaryKey(LinkItem record);
    
    int insertBatch(List<LinkItem> items);
}