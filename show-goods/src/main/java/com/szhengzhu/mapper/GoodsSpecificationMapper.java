package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.goods.GoodsSpecification;
import com.szhengzhu.bean.wechat.vo.StockBase;

public interface GoodsSpecificationMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(GoodsSpecification record);

    int insertSelective(GoodsSpecification record);

    GoodsSpecification selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(GoodsSpecification record);

    int updateByPrimaryKey(GoodsSpecification record);
    
    @Select("SELECT mark_id, goods_id, specification_ids, server_status, base_price, sale_price FROM t_goods_specification WHERE goods_id=#{goodsId}")
    @ResultMap("BaseResultMap")
    List<GoodsSpecification> selectByGoods(@Param("goodsId") String goodsId);
    
    List<GoodsSpecification> selectByExampleSelective(GoodsSpecification specification);
    
    int insertBatch(List<GoodsSpecification> specifications);
    
    StockBase selectSpecGoodsInfo(@Param("goodsId") String goodsId, @Param("specIds") String specIds);
}