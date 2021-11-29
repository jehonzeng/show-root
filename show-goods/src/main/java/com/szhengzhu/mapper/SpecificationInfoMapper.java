package com.szhengzhu.mapper;

import com.szhengzhu.bean.goods.SpecificationInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.SpecChooseBox;
import com.szhengzhu.provider.GoodsProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Administrator
 */
public interface SpecificationInfoMapper {

    int deleteByPrimaryKey(String markId);

    int insert(SpecificationInfo record);

    int insertSelective(SpecificationInfo record);

    SpecificationInfo selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(SpecificationInfo record);

    int updateByPrimaryKey(SpecificationInfo record);
    
    int insertBatch(List<SpecificationInfo> record);
    
    List<SpecificationInfo> selectInByType(SpecificationInfo specification);

    List<SpecificationInfo> selectNotInByType(SpecificationInfo specification);

    List<SpecificationInfo> selectByExampleSelective(SpecificationInfo record);

    @Select("SELECT COUNT(*) FROM t_specification_info WHERE attr_value = #{attrValue} AND attr_name = #{attrName} AND mark_id <> #{markId} ")
    int repeatRecords(@Param("attrValue") String attrValue, @Param("attrName") String attrName,
            @Param("markId") String markId);

    @Select("SELECT i.mark_id AS code,i.attr_value AS name FROM t_specification_info i LEFT JOIN t_type_specification s ON s.specification_id=i.mark_id WHERE i.attr_name = #{attrName} AND i.server_status = 1 ORDER BY s.sort")
    List<Combobox> selectByName(@Param("attrName") String attrName);

    @SelectProvider(type = GoodsProvider.class, method = "selectNameByGoodsId")
    @Results({ 
        @Result(property = "attrName", column = "attrName"),
        @Result(property = "list", column = "attrName", 
        many = @Many(select = "com.szhengzhu.mapper.SpecificationInfoMapper.selectByName")) })
    List<SpecChooseBox> selectNameByGoodsId(@Param("goodsId") String goodsId);
}