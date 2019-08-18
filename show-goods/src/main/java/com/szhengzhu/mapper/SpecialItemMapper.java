package com.szhengzhu.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.szhengzhu.bean.goods.SpecialItem;
import com.szhengzhu.bean.vo.SpecialGoodsVo;

public interface SpecialItemMapper {
    int deleteByPrimaryKey(String markId);

    int insert(SpecialItem record);

    int insertSelective(SpecialItem record);

    SpecialItem selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(SpecialItem record);

    int updateByPrimaryKey(SpecialItem record);

    int insertBatch(List<SpecialItem> items);

    List<SpecialGoodsVo> selectByExampleSelective(SpecialItem data);

    @Select("select count(*) from t_special_item where goods_id = #{goodsId}")
    int hasItem(@Param("goodsId") String goodsId);

    @Update("update t_special_item set special_id = #{specialId} where goods_id = #{goodsId} ")
    int updateByGoods(@Param("goodsId") String goodsId, @Param("specialId") String specialId);

    @Delete("delete from t_special_item where special_id = #{specialId} and goods_id = #{goodsId}")
    int deleteItem(@Param("specialId") String specialId, @Param("goodsId") String goodsId);
    
    BigDecimal selectGoodsSalePrice(@Param("goodsId") String goodsId, @Param("spcialId") String spcialId);
}