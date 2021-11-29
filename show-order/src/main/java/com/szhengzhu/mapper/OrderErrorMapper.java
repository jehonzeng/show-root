package com.szhengzhu.mapper;

import com.szhengzhu.bean.order.OrderError;

/**
 * @author Jehon Zeng
 */
public interface OrderErrorMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(OrderError record);

    int insertSelective(OrderError record);

    OrderError selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(OrderError record);

    int updateByPrimaryKey(OrderError record);
}