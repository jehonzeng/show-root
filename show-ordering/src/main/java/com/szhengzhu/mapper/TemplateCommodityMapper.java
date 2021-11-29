package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.ordering.TemplateCommodityKey;

public interface TemplateCommodityMapper {
    int deleteByPrimaryKey(TemplateCommodityKey key);

    int insert(TemplateCommodityKey record);

    int insertSelective(TemplateCommodityKey record);

    int insertBatchCommodity(@Param("templateId")String templateId, @Param("commodityIds")String[] commodityIds);

    @Select("select commodity_id from t_template_commodity where template_id = #{templateId}")
    List<String> selectCommodityIds(@Param("templateId") String templateId);

    @Delete("delete from t_template_commodity where template_id = #{templateId}")
    int deleteByTemplateId(@Param("templateId") String templateId);
}