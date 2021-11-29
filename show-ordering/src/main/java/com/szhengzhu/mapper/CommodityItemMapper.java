package com.szhengzhu.mapper;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.ordering.CommodityItem;

public interface CommodityItemMapper {
    
    int deleteByPrimaryKey(@Param("commodityId") String commodityId, @Param("specsId") String specsId, @Param("itemId") String itemId);

    int insert(CommodityItem record);

    int insertSelective(CommodityItem record);

    CommodityItem selectByPrimaryKey(@Param("commodityId") String commodityId, @Param("specsId") String specsId, @Param("itemId") String itemId);

    int updateByPrimaryKeySelective(CommodityItem record);

    int updateByPrimaryKey(CommodityItem record);
    
//    List<SpecsModel> selectLjsCommItem(@Param("commodityId") String commodityId);
    
    @Select("SELECT IFNULL(SUM(IFNULL(markup_price, 0)), 0) FROM t_commodity_item WHERE FIND_IN_SET(item_id, #{specsItems}) and commodity_id=#{commodityId}")
    BigDecimal sumItemPrice(@Param("commodityId") String commodityId, @Param("specsItems") String specsItems);
}