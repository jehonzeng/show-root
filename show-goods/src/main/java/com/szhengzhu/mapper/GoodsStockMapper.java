package com.szhengzhu.mapper;

import com.szhengzhu.bean.goods.GoodsStock;
import com.szhengzhu.bean.vo.GoodsBaseVo;
import com.szhengzhu.bean.vo.StockVo;
import com.szhengzhu.provider.StockProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
public interface GoodsStockMapper {

    int deleteByPrimaryKey(String markId);

    int insert(GoodsStock record);

    int insertSelective(GoodsStock record);

    GoodsStock selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(GoodsStock record);

    int updateByPrimaryKey(GoodsStock record);

    List<StockVo> selectByExampleSelective(GoodsStock record);

    @SelectProvider(type = StockProvider.class, method = "selectInfos")
    GoodsBaseVo selectInfos(@Param("markId") String markId);
    
    List<StockVo> selectGoodsStockIds(List<String> markIds);
    
    int insertBatch(List<GoodsStock> stocks);
    
    @Select("SELECT specification_ids FROM t_goods_stock WHERE goods_id=#{goodsId} AND storehouse_id=#{storeHouseId}")
    List<String> selectGoodsStockSpec(@Param("goodsId") String goodsId, @Param("storeHouseId") String storeHouseId);
    
    Map<String, Integer> selectDeliveryAndStock(@Param("addressId") String addressId, @Param("goodsId") String goodsId, @Param("specIds") String specIds);
    
    GoodsStock selectGoodsStock(@Param("goodsId") String goodsId, @Param("specificationIds") String specificationIds);
    
    GoodsStock selectGoodsStockByAddress(@Param("addressId") String addressId, @Param("goodsId") String goodsId, @Param("specificationIds") String specificationIds);
    
    @Update("update t_goods_stock SET current_stock=current_stock - #{quantity} WHERE goods_id = #{goodsId} AND specification_ids = #{specIds} AND storehouse_id=#{storehouseId}")
    void subCurrentStock(@Param("goodsId") String goodsId, @Param("specIds") String specIds, @Param("storehouseId") String storehouseId, @Param("quantity") int quantity);
    
    @Update("update t_goods_stock SET total_stock=total_stock - #{quantity} WHERE goods_id = #{goodsId} AND specification_ids = #{specIds} AND storehouse_id=#{storehouseId}")
    void subTotalStock(@Param("goodsId") String goodsId, @Param("specIds") String specIds, @Param("storehouseId") String storehouseId, @Param("quantity") int quantity);
    
    @Update("update t_goods_stock SET current_stock=current_stock + #{quantity} WHERE goods_id = #{goodsId} AND specification_ids = #{specIds} AND storehouse_id=#{storehouseId}")
    void addCurrentStock(@Param("goodsId") String goodsId, @Param("specIds") String specIds, @Param("storehouseId") String storehouseId, @Param("quantity") int quantity);
    
    @Update("update t_goods_stock SET total_stock=total_stock + #{quantity} WHERE goods_id = #{goodsId} AND specification_ids = #{specIds} AND storehouse_id=#{storehouseId}")
    void addTotalStock(@Param("goodsId") String goodsId, @Param("specIds") String specIds, @Param("storehouseId") String storehouseId, @Param("quantity") int quantity);
    
    
}