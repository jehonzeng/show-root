package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.ordering.MarketCommodity;

public interface MarketCommodityMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(MarketCommodity record);

    int insertSelective(MarketCommodity record);

    MarketCommodity selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(MarketCommodity record);

    int updateByPrimaryKey(MarketCommodity record);
    
    int insertBatch(List<MarketCommodity> list);
    
    @Delete("delete from t_market_commodity where market_id=#{marketId}")
    int deleteComm(@Param("marketId") String marketId);
    
    @Select("select commodity_id from t_market_commodity where market_id=#{marketId} and type=#{type}")
    List<String> selectCommIds(@Param("marketId") String marketId, @Param("type") Integer type);
    
    Integer selectExistComm(List<String> commodityIds);
}