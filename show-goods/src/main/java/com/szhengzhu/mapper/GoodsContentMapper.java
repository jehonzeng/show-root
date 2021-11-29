package com.szhengzhu.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.goods.GoodsContent;

/**
 * @author Administrator
 */
public interface GoodsContentMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(GoodsContent record);

    int insertSelective(GoodsContent record);

    GoodsContent selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(GoodsContent record);

    int updateByPrimaryKeyWithBLOBs(GoodsContent record);

    int updateByPrimaryKey(GoodsContent record);
    
    @Select("SELECT mark_id AS markId,goods_id AS goodsId,content AS content FROM t_goods_content WHERE goods_id = #{goodsId}")
    GoodsContent selectByGoodsId(@Param("goodsId") String goodsId);
}