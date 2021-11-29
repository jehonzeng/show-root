package com.szhengzhu.mapper;

import java.util.List;

import com.szhengzhu.bean.order.BackHistory;

/**
 * @author Jehon Zeng
 */
public interface BackHistoryMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(BackHistory record);

    int insertSelective(BackHistory record);

    BackHistory selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(BackHistory record);

    int updateByPrimaryKey(BackHistory record);
    
    List<BackHistory> selectByExampleSelective(BackHistory backHistory);
}