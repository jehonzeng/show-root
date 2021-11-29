package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.szhengzhu.bean.activity.SceneGoods;

public interface SceneGoodsMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(SceneGoods record);

    int insertSelective(SceneGoods record);

    SceneGoods selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(SceneGoods record);

    int updateByPrimaryKey(SceneGoods record);
    
    List<SceneGoods> selectByExampleSelective(SceneGoods goods);
    
    List<SceneGoods> selectForeGoodsList();
    
    @Update("UPDATE t_scene_goods SET stock_size=stock_size-#{quantity} WHERE mark_id=#{goodsId}")
    void subStock(@Param("goodsId") String goodsId, @Param("quantity") int quantity);
    
    @Update("UPDATE t_scene_goods SET stock_size=stock_size+#{quantity} WHERE mark_id=#{goodsId}")
    void addStock(@Param("goodsId") String goodsId, @Param("quantity") int quantity);
    
    @Update("UPDATE t_scene_goods SET receive_size=receive_size-#{quantity} WHERE mark_id=#{goodsId}")
    void receive(@Param("goodsId") String goodsId, @Param("quantity") int quantity);
    
    List<SceneGoods> selectByScenId(@Param("sceneId") String sceneId);
}