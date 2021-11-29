package com.szhengzhu.mapper;

import com.szhengzhu.bean.goods.MealItem;
import com.szhengzhu.bean.vo.MealVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Administrator
 */
public interface MealItemMapper {

    int deleteByPrimaryKey(String markId);

    int insert(MealItem record);

    int insertSelective(MealItem record);

    MealItem selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(MealItem record);

    int updateByPrimaryKey(MealItem record);

    int insertBatch(List<MealItem> items);

    @Select("select count(*) from t_meal_item where meal_id = #{mealId} and goods_id=#{goodsId} and specification_ids = #{specificationIds} and mark_id <> #{markId}")
    int repeatRecords(@Param("mealId") String mealId, @Param("goodsId") String goodsId,
            @Param("specificationIds") String specificationIds,@Param("markId") String marklId);

    List<MealVo> selectByExampleSelective(MealItem data);
    
    @Select("SELECT mark_id, meal_id, goods_id,specification_ids, quantity, sort FROM t_meal_item WHERE meal_id = #{mealId}")
    @ResultMap("BaseResultMap")
    List<MealItem> selectItemByMeal(@Param("mealId") String mealId);
}