package com.szhengzhu.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.szhengzhu.bean.order.SeckillOrder;

public interface SeckillOrderMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(SeckillOrder record);

    int insertSelective(SeckillOrder record);

    SeckillOrder selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(SeckillOrder record);

    int updateByPrimaryKey(SeckillOrder record);
    
    List<SeckillOrder> selectByExampleSelective(SeckillOrder seckillOrder);
    
    @Update("UPDATE t_seckill_order SET delivery_date=#{deliveryDate} WHERE mark_id=#{markId}")
    int updateDeliveryDate(@Param("markId") String markId, @Param("deliveryDate") Date deliveryDate);
    
    List<String> selectItemImg(@Param("orderId") String orderId);
    
    SeckillOrder selectByNo(@Param("orderNo") String orderNo);
}