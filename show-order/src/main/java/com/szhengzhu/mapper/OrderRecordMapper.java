package com.szhengzhu.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.order.OrderRecord;

public interface OrderRecordMapper {
    
    int insert(OrderRecord record);

    int insertSelective(OrderRecord record);
    
    @Select("SELECT GROUP_CONCAT(reason separator '##') FROM t_order_record WHERE order_no=#{orderNo}")
    String selectOrderRecord(@Param("orderNo") String orderNo);
}