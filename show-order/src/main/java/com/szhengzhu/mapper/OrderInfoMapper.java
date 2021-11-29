package com.szhengzhu.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.szhengzhu.bean.excel.ProductModel;
import com.szhengzhu.bean.excel.SauceVo;
import com.szhengzhu.bean.order.OrderInfo;
import com.szhengzhu.bean.rpt.IndexDisplay;
import com.szhengzhu.bean.wechat.vo.OrderDetail;

/**
 * @author Jehon Zeng
 */
public interface OrderInfoMapper {

    int deleteByPrimaryKey(String markId);

    int insert(OrderInfo record);

    int insertSelective(OrderInfo record);

    OrderInfo selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);

    List<OrderInfo> selectByExampleSelective(OrderInfo orderInfo);
    
    OrderInfo selectByNo(@Param("orderNo") String orderNo, @Param("userId") String userId);
    
    @Update("UPDATE t_order_info SET delivery_date=#{deliveryDate} WHERE mark_id=#{markId}")
    int updateDeliveryDate(@Param("markId") String markId, @Param("deliveryDate") Date deliveryDate);
    
    OrderDetail selectOrderDetail(@Param("orderNo") String orderNo, @Param("userId") String userId);
    
    @Update("UPDATE t_order_info SET order_status=#{orderStatus}, pay_time=NOW() WHERE order_no=#{orderNo}")
    int updateOrderPayByNo(@Param("orderNo") String orderNo, @Param("orderStatus") String orderStatus);
    
    @Update("UPDATE t_order_info SET order_status='OT10', cancel_time=NOW() WHERE order_status='OT01' AND UNIX_TIMESTAMP(NOW()) - UNIX_TIMESTAMP(order_time) > 15 * 60 AND user_id=#{userId}")
    int cancelExpiredOrder(@Param("userId") String userId);
    
//    @Update("UPDATE t_order_info SET order_status='OT10', cancel_time=NOW() WHERE order_status='OT01' AND UNIX_TIMESTAMP(NOW()) - UNIX_TIMESTAMP(order_time) > 15 * 60")
//    int cancelAllExpiredOrder();
    
    List<OrderInfo> selectExpiredOrder();
    
    @Update("UPDATE t_order_info SET order_status='OT10', cancel_time=NOW() WHERE order_status='OT01' AND UNIX_TIMESTAMP(NOW()) - UNIX_TIMESTAMP(order_time) > 15 * 60 AND mark_id=#{orderId}")
    int cancelExpiredOrderById(@Param("orderId") String orderId);
    
    List<OrderInfo> selectStatusOrder(@Param("orderStatus") String orderStatus);
    
    @Select("SELECT order_no FROM t_order_info WHERE order_status = #{orderStatus}")
    List<String> selectOrderNoByStatus(@Param("orderStatus") String orderStatus);
    
    @Update("UPDATE t_order_info SET order_status='OT05', arrive_time=NOW() WHERE TO_DAYS(NOW()) - TO_DAYS(order_time) >= 15 AND order_status='OT04'")
    int updateOrderReceive();

    @Select("SELECT CASE WHEN sauceCount = 1 THEN '一瓶' WHEN sauceCount = 2 THEN '两瓶' WHEN sauceCount = 3 THEN '三瓶' WHEN sauceCount = 4 THEN '四瓶' WHEN sauceCount >=5 THEN '五瓶及以上' END 'sauceName',count(*) AS orderCount FROM v_sauce_data GROUP BY sauceCount")
    List<SauceVo> selectSauceOrderCount();

    @Select("SELECT i.product_id AS productId,i.product_name AS productName,SUM(IFNULL(i.quantity,0)) AS productCount FROM t_order_info o LEFT JOIN t_order_item i ON i.order_id = o.mark_id WHERE o.order_status = 'OT03' GROUP BY i.product_id ")
    List<ProductModel> selectProductCount();

    @Update("UPDATE t_order_info SET order_status = 'OT04' WHERE order_no = #{orderNo} AND order_status ='OT03' ")
    int updateStatusByOrderNo(@Param("orderNo") String orderNo);

    @Select("SELECT count(1) FROM t_order_info WHERE user_id=#{userId} AND order_status in ('OT02','OT03','OT04','OT05','OT06')")
    int selectCountByUserId(@Param("userId") String userId);
    
    List<IndexDisplay> selectIndexStatusCount();

    int batchUpdateStatus(List<OrderInfo> list);
    
    List<OrderInfo> selectChooseOrder(List<String> list);
    
    List<OrderInfo> selectShareOrderByExampleSelective(OrderInfo orderInfo);
}