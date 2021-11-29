package com.szhengzhu.mapper;

import com.szhengzhu.bean.goods.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Administrator
 */
public interface ShoppingCartMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(ShoppingCart record);

    int insertSelective(ShoppingCart record);

    ShoppingCart selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(ShoppingCart record);

    int updateByPrimaryKey(ShoppingCart record);
    
    ShoppingCart selectSingleCart(@Param("userId") String userId, @Param("productId") String productId, @Param("specIds") String specIds);
    
    List<ShoppingCart> selectByUser(@Param("userId") String userId);
    
    @Delete("DELETE FROM t_shopping_cart WHERE user_id=#{userId}")
    int deleteUserCart(@Param("userId") String userId);
    
    int insertBatch(List<ShoppingCart> items);
}