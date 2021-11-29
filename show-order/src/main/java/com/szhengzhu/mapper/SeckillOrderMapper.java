package com.szhengzhu.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.szhengzhu.bean.order.SeckillOrder;
import com.szhengzhu.bean.vo.ExportTemplateVo;

/**
 * @author Jehon Zeng
 */
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
    
    @Select("SELECT g.image_path FROM t_seckill_order o LEFT JOIN db_goods.t_goods_image g ON g.goods_id=o.goods_id WHERE g.server_type=0 AND o.mark_id=#{orderId} LIMIT 1")
    List<String> selectItemImg(@Param("orderId") String orderId);
    
    SeckillOrder selectByNo(@Param("orderNo") String orderNo, @Param("userId") String userId);

    @Select("SELECT d.contact AS userName,d.phone AS userPhone,d.delivery_area AS userArea,d.delivery_address AS userAddress,s.product_name AS productName,s.quantity FROM t_seckill_order s INNER JOIN t_order_delivery d ON d.order_id = s.mark_id where s.mark_id = #{markId}")
    ExportTemplateVo selectOrderById(@Param("markId") String markId);
    
    @Select("SELECT count(1) FROM t_seckill_order WHERE user_id=#{userId} AND order_status in ('OT02','OT03','OT04','OT05','OT06')")
    int selectCountByUserId(@Param("userId") String userId);
    
    List<SeckillOrder> selectExpiredOrder();
}