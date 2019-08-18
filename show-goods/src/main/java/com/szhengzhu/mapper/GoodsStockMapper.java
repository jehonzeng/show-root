package com.szhengzhu.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.szhengzhu.bean.goods.GoodsStock;
import com.szhengzhu.bean.vo.GoodsBaseVo;
import com.szhengzhu.bean.vo.StockVo;
import com.szhengzhu.provider.StockProvider;

public interface GoodsStockMapper {

    int deleteByPrimaryKey(String markId);

    int insert(GoodsStock record);

    int insertSelective(GoodsStock record);

    GoodsStock selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(GoodsStock record);

    int updateByPrimaryKey(GoodsStock record);

//    @Select("SELECT COUNT(*) FROM t_goods_stock WHERE goods_id = #{goodsId} AND storehouse_id = #{storehouseId} AND specification_ids = #{specificationIds} AND mark_id <> #{markId}")
//    int repeatRecords(@Param("goodsId") String goodsId, @Param("storehouseId") String storehouseId,
//            @Param("specificationIds") String specificationIds, @Param("markId") String markId);

    List<StockVo> selectByExampleSelective(GoodsStock record);

    @SelectProvider(type = StockProvider.class, method = "selectInfos")
    GoodsBaseVo selectInfos(@Param("markId") String markId);
    
    List<StockVo> selectGoodsStocks(List<String> markIds);
    
    int insertBatch(List<GoodsStock> stocks);
    
//    @Select("SELECT mark_id AS markId,goods_id AS goodsId,specification_ids AS specificationIds,storehouse_id AS storehouseId FROM t_goods_stock "
//            + " WHERE goods_id=#{goodsId} AND storehouse_id=#{storehouseId}")
//    List<GoodsStock> goodsHouseStock(@Param("goodsId") String goodsId, @Param("storehouseId") String storehouseId);
    
    @Select("SELECT specification_ids FROM t_goods_stock WHERE goods_id=#{goodsId} AND storehouse_id=#{soreHouseId}")
    List<String> selectGoodsStockSpec(@Param("goodsId") String goodsId, @Param("soreHouseId") String soreHouseId);
    
    Map<String, Integer> selectDeliveryAndStock(@Param("addressId") String addressId, @Param("goodsId") String goodsId, @Param("specIds") String specIds);
}