package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.goods.FoodsInfo;
import com.szhengzhu.bean.vo.Combobox;

public interface FoodsInfoMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(FoodsInfo record);

    int insertSelective(FoodsInfo record);

    FoodsInfo selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(FoodsInfo record);

    int updateByPrimaryKey(FoodsInfo record);
    
    @Select("SELECT COUNT(*) FROM t_food_info WHERE food_name= #{foodName} AND mark_id <> #{markId}")
    int repeatRecords(@Param("foodName") String foodName, @Param("markId") String markId);
    
    List<FoodsInfo> selectByExampleSelective(FoodsInfo record);

    @Select("SELECT f.mark_id AS code,f.food_name AS name FROM t_food_info f  WHERE NOT EXISTS (SELECT * FROM t_food_item i WHERE i.goods_id = #{goodsId} AND i.food_id = f.mark_id ) AND f.server_status = 1 ")
    List<Combobox> selectComboboxList(@Param("goodsId") String goodsId);

    @Select("SELECT mark_id AS code,food_name AS name FROM t_food_info WHERE server_status = 1")
    List<Combobox> selectFoodList();
}