package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.goods.MealImage;

public interface MealImageMapper {
    int deleteByPrimaryKey(String markId);

    int insert(MealImage record);

    int insertSelective(MealImage record);

    MealImage selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(MealImage record);

    int updateByPrimaryKey(MealImage record);

    @Select("select mark_id AS markId,meal_id AS mealId,image_path AS imagePath,server_type AS serverType,sort FROM t_meal_image WHERE meal_id = #{mealId} AND server_type = #{type} ORDER BY sort")
    List<MealImage> selectListByTypeAndId(@Param("mealId")String mealId, @Param("type")Integer type);
    
    @Select("SELECT image_path FROM t_meal_image WHERE server_type=2 AND meal_id=#{mealId} ORDER BY sort")
    List<String> selectBigByMealId(@Param("mealId") String mealId);
}