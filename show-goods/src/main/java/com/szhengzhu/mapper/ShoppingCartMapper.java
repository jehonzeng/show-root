package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.szhengzhu.bean.goods.ShoppingCart;

public interface ShoppingCartMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(ShoppingCart record);

    int insertSelective(ShoppingCart record);

    ShoppingCart selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(ShoppingCart record);

    int updateByPrimaryKey(ShoppingCart record);
    
    ShoppingCart selectSingleCart(@Param("userId") String userId, @Param("productId") String productId, @Param("specIds") String specIds);
    
    List<ShoppingCart> selectByUser(@Param("userId") String userId);
}