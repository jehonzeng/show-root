package com.szhengzhu.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.szhengzhu.bean.order.OrderInfo;
import com.szhengzhu.bean.wechat.vo.OrderBase;
import com.szhengzhu.bean.wechat.vo.OrderDetail;

public interface OrderInfoMapper {

    int deleteByPrimaryKey(String markId);

    int insert(OrderInfo record);

    int insertSelective(OrderInfo record);

    OrderInfo selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);

    List<OrderInfo> selectByExampleSelective(OrderInfo orderInfo);
    
    OrderInfo selectByNo(@Param("orderNo") String orderNo);
    
    @Update("UPDATE t_order_info SET delivery_date=#{deliveryDate} WHERE mark_id=#{markId}")
    int updateDeliveryDate(@Param("markId") String markId, @Param("deliveryDate") Date deliveryDate);
    
    List<OrderBase> selectAll(@Param("userId") String userId);
    
    List<OrderBase> selectUnpaid(@Param("userId") String userId);
    
    List<OrderBase> selectUngroup(@Param("userId") String userId);
    
    List<OrderBase> selectUndelivery(@Param("userId") String userId);
    
    List<OrderBase> selectUnReceive(@Param("userId") String userId);
    
    List<OrderBase> selectUnjudge(@Param("userId") String userId);
    
    OrderDetail selectOrderDetail(@Param("orderNo") String orderNo);
    
    @Update("UPDATE t_order_info SET order_status=#{orderStatus}, pay_time=NOW() WHERE order_no=#{orderNo}")
    int updateOrderByNo(@Param("orderNo") String orderNo, @Param("orderStatus") String orderStatus);
}