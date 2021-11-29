package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.szhengzhu.bean.activity.SceneItem;

public interface SceneItemMapper {
    int deleteByPrimaryKey(String markId);

    int insert(SceneItem record);

    int insertSelective(SceneItem record);

    SceneItem selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(SceneItem record);

    int updateByPrimaryKey(SceneItem record);
    
    List<SceneItem> selectByOrderId(@Param("orderId") String orderId);
    
    void insertBatch(List<SceneItem> items);
    
    List<SceneItem> selectUnReceiveGoods(@Param("userId") String userId);
    
    List<SceneItem> selectReceiveGoods(@Param("userId") String userId);
    
    @Update("UPDATE t_scene_item SET server_status = 1 WHERE mark_id = #{itemId}")
    void receiveGoods(@Param("itemId") String itemId);
    
    @Select("SELECT count(1) FROM t_scene_item i left join t_scene_order o on i.order_id=o.mark_id WHERE o.order_status='OT02' and o.user_id=#{userId} and i.mark_id = #{itemId} and i.server_status=0")
    int selectCountById(@Param("itemId") String itemId, @Param("userId") String userId);
}