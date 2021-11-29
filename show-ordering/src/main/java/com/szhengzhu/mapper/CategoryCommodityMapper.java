package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.ordering.CategoryCommodity;
import com.szhengzhu.bean.xwechat.vo.CategoryModel;

public interface CategoryCommodityMapper {
    
    int deleteByPrimaryKey(@Param("categoryId") String categoryId, @Param("commodityId") String commodityId);

    int insert(CategoryCommodity record);

    int insertSelective(CategoryCommodity record);

    CategoryCommodity selectByPrimaryKey(@Param("categoryId") String categoryId, @Param("commodityId") String commodityId);

    int updateByPrimaryKeySelective(CategoryCommodity record);

    int updateByPrimaryKey(CategoryCommodity record);
    
    int insertBatch(@Param("cateId") String cateId, @Param("commodityIds") String[] commodityIds);
    
    @Delete("delete from t_category_commodity where category_id = #{cateId,jdbcType=VARCHAR}")
    int deleteByCateId(@Param("cateId") String cateId);
    
    @Delete("delete from t_category_commodity where commodity_id=#{commodityId}")
    int deleteByCommodityId(@Param("commodityId") String commodityId);
    
    int insertCommodityBatch(@Param("cateIds") String[] cateIds, @Param("commodityId") String commodityId);
    
    @Select("select category_id from t_category_commodity where commodity_id=#{commodityId}")
    List<String> selectCateByCommodityId(@Param("commodityId") String commodityId);
    
    @Select("select commodity_id from t_category_commodity cc LEFT JOIN t_commodity_info c ON cc.commodity_id=c.mark_id where c.`status`<>-1 AND category_id=#{cateId} order by sort")
    List<String> selectCommodityByCateId(@Param("cateId") String cateId);
    
    List<CategoryModel> selectResCate(@Param("storeId") String storeId);
    
    List<CategoryModel> selectLjsCate(@Param("storeId") String storeId);
}