package com.szhengzhu.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.szhengzhu.bean.excel.OrderSendModel;
import com.szhengzhu.bean.excel.ProductModel;
import com.szhengzhu.bean.vo.TodayProductVo;
import com.szhengzhu.bean.wechat.vo.OrderBase;

/**
 * @author Jehon Zeng
 */
public interface OrderAllMapper {

    List<OrderBase> selectAll(@Param("userId") String userId);
    
    List<OrderBase> selectUnpaid(@Param("userId") String userId);
    
    List<OrderBase> selectUngroup(@Param("userId") String userId);
    
    List<OrderBase> selectUndelivery(@Param("userId") String userId);
    
    List<OrderBase> selectUnReceive(@Param("userId") String userId);
    
    List<OrderBase> selectUnjudge(@Param("userId") String userId);
    
    List<TodayProductVo> selectTodayItemList();
    
    List<Map<String, Object>> selectStatusCount(@Param("userId") String userId);
    
    List<OrderSendModel> selectSendInfo();
    
    List<ProductModel> selectTodayProductQuantity();
}
