package com.szhengzhu.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.goods.MealContent;

public interface MealContentMapper {
    int deleteByPrimaryKey(String markId);

    int insert(MealContent record);

    int insertSelective(MealContent record);

    MealContent selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(MealContent record);

    int updateByPrimaryKeyWithBLOBs(MealContent record);

    int updateByPrimaryKey(MealContent record);

    @Select("select mark_id AS markId,content from t_meal_content where meal_id = #{mealId}")
    MealContent selectByMealId(@Param("mealId") String mealId);
}