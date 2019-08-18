package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.goods.LabelGoods;
import com.szhengzhu.bean.vo.LabelGoodsVo;
import com.szhengzhu.bean.vo.LabelMealVo;

public interface LabelGoodsMapper {
    int deleteByPrimaryKey(String markId);

    int insert(LabelGoods record);

    int insertSelective(LabelGoods record);

    LabelGoods selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(LabelGoods record);

    int updateByPrimaryKey(LabelGoods record);

    List<LabelGoodsVo> selectByExampleSelective(LabelGoods record);
    
    int insertBatch(List<LabelGoods> list);
    
    @Select("SELECT mark_id AS markId,goods_id AS goodsId,label_id AS labelId,server_status AS serverStatus,sort FROM t_label_goods WHERE label_id = #{labelId}")
    List<LabelGoods> selectByLabel(@Param("labelId")String labelId);

    List<LabelMealVo> selectMealByExampleSelective(LabelGoods data);

    @Delete("DELETE FROM t_label_goods WHERE label_id = #{labelId} and goods_id = #{goodsId}")
    int deleteItem(@Param("labelId") String labelId,@Param("goodsId") String goodsId);
}