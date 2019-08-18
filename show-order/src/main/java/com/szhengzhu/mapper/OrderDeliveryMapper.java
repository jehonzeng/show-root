package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.szhengzhu.bean.order.OrderDelivery;

public interface OrderDeliveryMapper {

    int deleteByPrimaryKey(String markId);

    int insert(OrderDelivery record);

    int insertSelective(OrderDelivery record);

    OrderDelivery selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(OrderDelivery record);

    int updateByPrimaryKey(OrderDelivery record);

    List<OrderDelivery> selectByExampleSelective(OrderDelivery orderDelivery);

    OrderDelivery selectByOrderId(@Param("orderId") String orderId);
}