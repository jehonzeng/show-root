package com.szhengzhu.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.base.ProductContent;

public interface ProductContentMapper {
    int deleteByPrimaryKey(String markId);

    int insert(ProductContent record);

    int insertSelective(ProductContent record);

    ProductContent selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(ProductContent record);

    int updateByPrimaryKeyWithBLOBs(ProductContent record);

    int updateByPrimaryKey(ProductContent record);

    @Select("SELECT mark_id AS markId,content,product_id AS productId FROM t_product_content WHERE product_id = #{productId}")
    ProductContent selectByProductId(@Param("productId") String productId);

    @Insert("INSERT INTO t_product_content (mark_id,product_id,content) VALUES (#{markId},#{productId},#{content}) ON DUPLICATE KEY UPDATE content = VALUES(content) ")
    int insertOrUpdate(ProductContent content);
}