package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.order.OrderItem;
import com.szhengzhu.bean.wechat.vo.Judge;
import com.szhengzhu.bean.wechat.vo.OrderItemDetail;

public interface OrderItemMapper {

    int deleteByPrimaryKey(String markId);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);
    
    List<OrderItem> selectByOrderId(@Param("orderId") String orderId);
    
    int insertBatch(List<OrderItem> items);
    
    int deleteBatch(List<String> markIds);
    
    List<OrderItem> selectByExampleSelective(OrderItem orderItem);
    
    List<OrderItemDetail> selectItemDetail(@Param("orderId") String orderId);
    
    List<Judge> selectItemJudge(@Param("orderId") String orderId);
    
    @Select("SELECT GROUP_CONCAT(mark_id SEPARATOR ',') AS specIds FROM db_goods.t_specification_info " + 
            "WHERE mark_id in (SELECT s.specification_id FROM db_goods.t_type_specification s LEFT JOIN db_goods.t_goods_info g ON g.type_id=s.type_id WHERE g.mark_id=#{goodsId} AND s.default_or=1) " + 
            "ORDER BY mark_id")
    String selectDefaultByGoods(@Param("goodsId") String goodsId);
    
    List<String> selectItemImg(@Param("orderId") String orderId);
}