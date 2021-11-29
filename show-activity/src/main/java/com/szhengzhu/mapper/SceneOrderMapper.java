package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.szhengzhu.bean.activity.SceneOrder;

public interface SceneOrderMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(SceneOrder record);

    int insertSelective(SceneOrder record);

    SceneOrder selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(SceneOrder record);

    int updateByPrimaryKey(SceneOrder record);
    
    List<SceneOrder> selectByExampleSelective(SceneOrder order);
    
    SceneOrder selectByNo(@Param("orderNo") String orderNo, @Param("userId") String userId);
    
    @Update("UPDATE t_scene_order SET order_status=#{orderStatus} WHERE order_no=#{orderNo}")
    void updateStatus(@Param("orderNo") String orderNo, @Param("orderStatus") String orderStatus);
    
    List<SceneOrder> selectExpiredOrder();
}