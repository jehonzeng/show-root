package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.szhengzhu.bean.goods.GoodsImage;
import com.szhengzhu.provider.GoodsProvider;

public interface GoodsImageMapper {

    int deleteByPrimaryKey(String markId);

    int insert(GoodsImage record);

    int insertSelective(GoodsImage record);

    GoodsImage selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(GoodsImage record);

    int updateByPrimaryKey(GoodsImage record);

    @SelectProvider(type = GoodsProvider.class, method = "selectGoodsImageList")
    List<GoodsImage> selectGoodsImageList(@Param("goodsId") String goodsId);

    @Select("select mark_id AS markId,goods_id AS goodsId,specification_ids AS specificationIds,image_path AS imagePath,server_type AS serverType,sort AS sort FROM t_goods_image WHERE goods_id = #{goodsId} AND server_type = #{serverType} ORDER BY sort")
    List<GoodsImage> selectByGoodsIdAndType(@Param("goodsId") String goodsId,
            @Param("serverType") Integer serverType);
    
    @Select("SELECT image_path FROM t_goods_image WHERE server_type=2 AND goods_id=#{goodsId} ORDER BY sort")
    List<String> selectBigByGoodsId(@Param("goodsId") String goodsId);
}