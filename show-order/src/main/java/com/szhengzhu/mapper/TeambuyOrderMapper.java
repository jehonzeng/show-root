package com.szhengzhu.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.szhengzhu.bean.order.TeambuyOrder;
import com.szhengzhu.bean.vo.ExportTemplateVo;

/**
 * @author Jehon Zeng
 */
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

    @Select("SELECT g.image_path FROM t_teambuy_order o LEFT JOIN db_goods.t_goods_image g ON g.goods_id=o.goods_id WHERE g.server_type=0 AND o.mark_id=#{orderId} LIMIT 1")
    List<String> selectItemImg(@Param("orderId") String orderId);

    TeambuyOrder selectByNo(@Param("orderNo") String orderNo, @Param("userId") String userId);

    @Select("SELECT d.contact AS userName,d.phone AS userPhone,d.delivery_area AS userArea,d.delivery_address AS userAddress,t.product_name AS productName,t.quantity FROM t_teambuy_order t INNER JOIN t_order_delivery d ON d.order_id = t.mark_id where t.mark_id = #{markId}")
    ExportTemplateVo selectOrderById(@Param("markId") String markId);

    @Select("SELECT count(1) FROM t_teambuy_order WHERE user_id=#{userId} AND order_status in ('OT02','OT03','OT04','OT05','OT06')")
    int selectCountByUserId(@Param("userId") String userId);
    
    List<TeambuyOrder> selectExpiredOrder();
}