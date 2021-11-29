package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.szhengzhu.bean.ordering.CommoditySpecs;
import com.szhengzhu.bean.ordering.vo.CommoditySpecsVo;

public interface CommoditySpecsMapper {
    
    int deleteByPrimaryKey(@Param("commodityId") String commodityId, @Param("specsId") String specsId);

    int insert(CommoditySpecs record);

    int insertSelective(CommoditySpecs record);

    CommoditySpecs selectByPrimaryKey(@Param("commodityId") String commodityId, @Param("specsId") String specsId);

    int updateByPrimaryKeySelective(CommoditySpecs record);

    int updateByPrimaryKey(CommoditySpecs record);
    
    List<CommoditySpecsVo> selectVoByCommodityId(@Param("commodityId") String commodityId);
}