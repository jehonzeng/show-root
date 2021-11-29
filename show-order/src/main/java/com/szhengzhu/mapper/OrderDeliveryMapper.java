package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.szhengzhu.bean.excel.DeliveryModel;
import com.szhengzhu.bean.excel.LogisticsModel;
import com.szhengzhu.bean.order.OrderDelivery;
import com.szhengzhu.bean.vo.OrderExportVo;

/**
 * @author Jehon Zeng
 */
public interface OrderDeliveryMapper {

    int deleteByPrimaryKey(String markId);

    int insert(OrderDelivery record);

    int insertSelective(OrderDelivery record);

    OrderDelivery selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(OrderDelivery record);

    int updateByPrimaryKey(OrderDelivery record);

    List<OrderDelivery> selectByExampleSelective(OrderDelivery orderDelivery);

    OrderDelivery selectByOrderId(@Param("orderId") String orderId);

    @Select("SELECT d.contact AS userName,d.phone AS userPhone,d.delivery_area AS userArea,d.delivery_address AS userAddress FROM t_order_delivery d LEFT JOIN t_order_info o ON d.order_id = o.mark_id WHERE o.mark_id = #{markId}")
    OrderExportVo selectOrdersById(@Param("markId") String markId);
    
    @Select("SELECT o.order_no AS orderNo,d.contact AS userName,d.phone,CONCAT(d.delivery_area,d.delivery_address) AS userAddress FROM t_order_info o INNER JOIN t_order_delivery d ON d.order_id = o.mark_id WHERE o.order_status = 'OT03' ORDER BY o.order_time desc")
    List<DeliveryModel> selectTodayDeliverys();

    @Update("UPDATE t_order_delivery SET delivery_type = #{companyNo},track_no = #{trackNo} WHERE order_id IN (SELECT mark_id FROM t_order_info WHERE order_no = #{orderNo})")
    int updateDeliveryByOrderId(LogisticsModel logisticsModel);
}