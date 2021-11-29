package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.base.ProductInfo;
import com.szhengzhu.bean.vo.NewsVo;

public interface ProductInfoMapper {
    int deleteByPrimaryKey(String markId);

    int insert(ProductInfo record);

    int insertSelective(ProductInfo record);

    ProductInfo selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(ProductInfo record);

    int updateByPrimaryKey(ProductInfo record);
    
    @Select("SELECT count(*) FROM t_product_info WHERE name = #{name} AND mark_id <> #{markId} ")
    int repeatRecords(@Param("name")String name ,@Param("markId") String markId);

    List<ProductInfo> selectProductListByType(@Param("serverType") Integer serverType);

    @Select("SELECT p.name AS title,add_time AS time,c.content AS content FROM t_product_info p LEFT JOIN t_product_content c ON p.mark_id = c.product_id WHERE p.mark_id = #{markId}")
    NewsVo selectNewsById(@Param("markId") String markId);

    List<ProductInfo> selectByExampleSelective(ProductInfo data);
}