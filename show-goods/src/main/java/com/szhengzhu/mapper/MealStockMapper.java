package com.szhengzhu.mapper;

import com.szhengzhu.bean.goods.MealStock;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author Administrator
 */
public interface MealStockMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(MealStock record);

    int insertSelective(MealStock record);

    MealStock selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(MealStock record);

    int updateByPrimaryKey(MealStock record);
    
    List<MealStock> selectByExampleSelective(MealStock stock);
    
    @Select("SELECT count(1) FROM t_meal_stock WHERE meal_id = #{mealId} AND storehouse_id=#{storehouseId}")
    int selectCount(@Param("mealId") String mealId, @Param("storehouseId") String storehouseId);
    
    @Update("update t_meal_stock SET current_stock=current_stock - #{quantity} WHERE meal_id = #{mealId} AND storehouse_id=#{storehouseId}")
    void subCurrentStock(@Param("mealId") String mealId, @Param("storehouseId") String storehouseId, @Param("quantity") int quantity);
    
    @Update("update t_meal_stock SET total_stock=total_stock - #{quantity} WHERE meal_id = #{mealId} AND storehouse_id=#{storehouseId}")
    void subTotalStock(@Param("mealId") String mealId, @Param("storehouseId") String storehouseId, @Param("quantity") int quantity);
    
    @Update("update t_meal_stock SET current_stock=current_stock + #{quantity} WHERE meal_id = #{mealId} AND storehouse_id=#{storehouseId}")
    void addCurrentStock(@Param("mealId") String mealId, @Param("storehouseId") String storehouseId, @Param("quantity") int quantity);
    
    @Update("update t_meal_stock SET total_stock=total_stock + #{quantity} WHERE meal_id = #{mealId} AND storehouse_id=#{storehouseId}")
    void addTotalStock(@Param("mealId") String mealId, @Param("storehouseId") String storehouseId, @Param("quantity") int quantity);
    
    MealStock selectMealStockByAddress(@Param("mealId") String mealId, @Param("addressId") String addressId);
    
    MealStock selectMealStock(@Param("mealId") String mealId);
}