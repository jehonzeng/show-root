package com.szhengzhu.mapper;

import com.szhengzhu.bean.ordering.CommodityPrice;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface CommodityPriceMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(CommodityPrice record);

    int insertSelective(CommodityPrice record);

    CommodityPrice selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(CommodityPrice record);

    int updateByPrimaryKey(CommodityPrice record);
    
    List<CommodityPrice> selectByCommodityId(@Param("commodityId") String commodityId);
    
    @Update("update t_commodity_price set defaults = 0 where commodity_id = #{commodityId,jdbcType=VARCHAR}") 
    int cancelDefaultsByCommodityId(@Param("commodityId") String commodityId);
    
//    List<PriceModel> selectLjsCommPrice(@Param("commodityId") String commodityId);
    
    CommodityPrice selectByIdOrDefault(@Param("priceId") String priceId);
    
    @Update("update t_commodity_price set defaults = 1 where commodity_id = #{commodityId,jdbcType=VARCHAR} limit 1") 
    int updateDefaults(@Param("commodityId") String commodityId);

    @Update("update t_commodity_price set price_type = 0 where commodity_id = #{commodityId,jdbcType=VARCHAR}")
    int updateAllPriceType(@Param("commodityId") String commodityId);
}