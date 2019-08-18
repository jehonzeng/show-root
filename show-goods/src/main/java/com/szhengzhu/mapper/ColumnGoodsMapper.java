package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.goods.ColumnGoods;
import com.szhengzhu.bean.vo.ColumnGoodsVo;
import com.szhengzhu.bean.vo.ColumnMealVo;

public interface ColumnGoodsMapper {
    int deleteByPrimaryKey(String markId);

    int insert(ColumnGoods record);

    int insertSelective(ColumnGoods record);

    ColumnGoods selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(ColumnGoods record);

    int updateByPrimaryKey(ColumnGoods record);

    List<ColumnGoodsVo> selectByExampleSelective(ColumnGoods base);

    int insertBatch(List<ColumnGoods> list);

    @Select("SELECT mark_id AS markId,goods_id AS goodsId,column_id AS columnId,server_status AS serverStatus,sort FROM t_column_goods WHERE column_id = #{columnId}")
    List<ColumnGoods> selectByColumn(@Param("columnId")String columnId);
    
    List<ColumnMealVo> selectMealByExampleSelective(ColumnGoods base);

    @Delete("DELETE FROM t_column_goods WHERE column_id = #{coulmnId} AND goods_id = #{goodsId}")
    int deleteItem(@Param("coulmnId") String columnId,@Param("goodsId") String goodsId);

}