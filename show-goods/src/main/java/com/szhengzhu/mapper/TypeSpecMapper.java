package com.szhengzhu.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.goods.TypeSpec;

public interface TypeSpecMapper {
    
    int deleteByPrimaryKey(@Param("typeId") String typeId, @Param("specificationId") String specificationId);

    int insert(TypeSpec record);

    int insertSelective(TypeSpec record);

    TypeSpec selectByPrimaryKey(@Param("typeId") String typeId, @Param("specificationId") String specificationId);

    int updateByPrimaryKeySelective(TypeSpec record);

    int updateByPrimaryKey(TypeSpec record);
    
    int insertBatch(@Param("typeId") String typeId, @Param("specIds") String[] specIds);
    
    @Select("SELECT COUNT(1) FROM t_type_specification WHERE specification_id IN (" + 
            "SELECT mark_id FROM t_specification_info WHERE attr_name IN (" + 
            "SELECT attr_name FROM t_specification_info WHERE mark_id=#{specId})) AND type_id=#{typeId} AND default_or=1 AND specification_id <> #{specId}")
    int existDefault(@Param("specId") String specId, @Param("typeId") String typeId);
    
    @Select("SELECT GROUP_CONCAT(mark_id SEPARATOR ',') AS specIds,GROUP_CONCAT(attr_value SEPARATOR ',') AS specValues FROM t_specification_info " + 
            "WHERE mark_id in (SELECT s.specification_id FROM t_type_specification s LEFT JOIN t_goods_info g ON g.type_id=s.type_id WHERE g.mark_id=#{goodsId} AND s.default_or=1) " + 
            "ORDER BY mark_id")
    Map<String, String> selectDefaultByGoods(@Param("goodsId") String goodsId);
}