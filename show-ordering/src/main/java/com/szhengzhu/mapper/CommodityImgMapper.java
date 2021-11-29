package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.ordering.CommodityImg;

public interface CommodityImgMapper {
    
    int deleteByPrimaryKey(@Param("commodityId") String commodityId, @Param("imgId") String imgId);

    int insert(CommodityImg record);

    int insertSelective(CommodityImg record);

    CommodityImg selectByPrimaryKey(@Param("commodityId") String commodityId, @Param("imgId") String imgId);

    int updateByPrimaryKeySelective(CommodityImg record);

    int updateByPrimaryKey(CommodityImg record);
    
    int insertBatch(@Param("imgIdList") String[] imgIdList, @Param("commodityId") String commodityId);
    
    @Delete("delete from t_commodity_img where commodity_id = #{commodityId}")
    int deleteByCommodityId(@Param("commodityId") String commodityId);
    
    @Select("select img_id from t_commodity_img where commodity_id = #{commodityId}")
    List<String> selectImgIdByCommodityId(@Param("commodityId") String commodityId);
}