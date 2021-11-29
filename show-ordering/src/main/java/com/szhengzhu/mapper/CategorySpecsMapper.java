package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.ordering.CategorySpecs;

public interface CategorySpecsMapper {
    
    int deleteByPrimaryKey(@Param("categoryId") String categoryId, @Param("specsId") String specsId);

    int insert(CategorySpecs record);

    CategorySpecs selectByPrimaryKey(@Param("categoryId") String categoryId, @Param("specsId") String specsId);
    
    int updateByPrimaryKey(CategorySpecs record);
    
    int insertBatch(@Param("cateId") String cateId, @Param("specsIds") String[] specsIds);
    
    @Delete("delete from t_category_specs where category_id = #{cateId}")
    int deleteByCateId(@Param("cateId") String cateId);
    
    @Select("select specs_id from t_category_specs where category_id = #{cateId}")
    List<String> selectSpecsByCateId(@Param("cateId") String cateId);
}