package com.szhengzhu.mapper;

import java.util.List;

import com.szhengzhu.bean.order.RefundBack;

/**
 * @author Jehon Zeng
 */
public interface RefundBackMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(RefundBack record);

    int insertSelective(RefundBack record);

    RefundBack selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(RefundBack record);

    int updateByPrimaryKey(RefundBack record);
    
    List<RefundBack> selectByExampleSelective(RefundBack refundBack);
}