package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.goods.MealServer;
import com.szhengzhu.bean.vo.BatchVo;

public interface MealServerMapper {
    int deleteByPrimaryKey(String markId);

    int insert(MealServer record);

    int insertSelective(MealServer record);

    MealServer selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(MealServer record);

    int updateByPrimaryKey(MealServer record);
    
    int insertBatch(List<MealServer> list);
    
    int deletetBatch(BatchVo base);
    
    @Select("SELECT server_id FROM t_meal_server WHERE meal_id = #{mealId} ")
    List<String> selectServerListByMeal(@Param("mealId") String mealId);
}