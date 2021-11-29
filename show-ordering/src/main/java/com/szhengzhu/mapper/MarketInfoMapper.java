package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.szhengzhu.bean.ordering.MarketInfo;

public interface MarketInfoMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(MarketInfo record);

    int insertSelective(MarketInfo record);

    MarketInfo selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(MarketInfo record);

    int updateByPrimaryKey(MarketInfo record);
    
    List<MarketInfo> selectByExampleSelective(MarketInfo marketInfo);
    
    List<MarketInfo> selectCartMarket(@Param("tableId") String tableId);
    
    List<MarketInfo> selectIndentMarket(@Param("indentId") String indentId, @Param("storeId") String storeId);
}