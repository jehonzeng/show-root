package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.szhengzhu.bean.goods.IconItem;
import com.szhengzhu.bean.vo.IconGoodsVo;

public interface IconItemMapper {
    int deleteByPrimaryKey(String markId);

    int insert(IconItem record);

    int insertSelective(IconItem record);

    IconItem selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(IconItem record);

    int updateByPrimaryKey(IconItem record);

    List<IconGoodsVo> selectByExampleSelective(IconItem data);

    @Delete("delete from t_icon_item where icon_id = #{iconId} and goods_id = #{goodsId}")
    int deleteItem(@Param("iconId") String iconId, @Param("goodsId") String goodsId);

    @Select("select count(*) from t_icon_item where goods_id = #{goodsId}")
    int hasItem(@Param("goodsId") String goodsId);
    
    @Update("update t_icon_item set icon_id = #{iconId} where goods_id = #{goodsId}")
    int updateByGoods(@Param("goodsId") String goodsId ,@Param("iconId")String iconId);

    int insertBatch(List<IconItem> list);
}