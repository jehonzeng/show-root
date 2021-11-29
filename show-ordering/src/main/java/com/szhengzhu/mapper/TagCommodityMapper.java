package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.ordering.TagCommodity;

public interface TagCommodityMapper {
    
    int deleteByPrimaryKey(TagCommodity record);

    int insert(TagCommodity record);

    int insertBatch(@Param("commodityIds") String[] commodityIds, @Param("tagId") String tagId);
    
    int deleteBatch(@Param("commodityIds") String[] commodityIds, @Param("tagId") String tagId);
    
    @Delete("delete from t_tag_commodity where commodity_id=#{commodityId}")
    int deleteByCommodityId(@Param("commodityId") String commodityId);
    
    int insertCommodityBatch(@Param("commodityId") String commodityId, @Param("tagIds") String[] tagIds);
    
    @Select("select tag_id from t_tag_commodity where commodity_id=#{commodityId}")
    List<String> selectCommodityTag(@Param("commodityId") String commodityId);
    
//    @Select("SELECT img_id FROM t_tag_commodity tc LEFT JOIN t_tag_info ti ON tc.tag_id=ti.mark_id WHERE ti.`status`=1 AND tc.commodity_id=#{commodityId}")
//    List<String> selectCommodityTagImg(@Param("commodityId") String commodityId);
}