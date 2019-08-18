package com.szhengzhu.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.szhengzhu.bean.order.TeambuyOrder;

public interface TeambuyOrderMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(TeambuyOrder record);

    int insertSelective(TeambuyOrder record);

    TeambuyOrder selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(TeambuyOrder record);

    int updateByPrimaryKey(TeambuyOrder record);
    
    List<TeambuyOrder> selectByExampleSelective(TeambuyOrder order);
    
    @Update("UPDATE t_teambuy_item SET delivery_date=#{deliveryDate} WHERE mark_id=#{markId}")
    int updateDeliveryDate(@Param("markId") String markId, @Param("deliveryDate") Date deliveryDate);
    
    List<TeambuyOrder> selectByGroupId(@Param("orderId") String orderId);
    
    List<String> selectItemImg(@Param("orderId") String orderId);
    
    TeambuyOrder selectByNo(@Param("orderNo") String orderNo);
}