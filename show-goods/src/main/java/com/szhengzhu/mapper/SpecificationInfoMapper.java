package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.szhengzhu.bean.goods.SpecificationInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.SpecChooseBox;
import com.szhengzhu.provider.GoodsProvider;

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

    @Select("SELECT mark_id AS code,attr_value AS name FROM t_specification_info WHERE attr_name = #{attrName} ORDER BY sort")
    List<Combobox> selectByName(@Param("attrName") String attrName);

    @SelectProvider(type = GoodsProvider.class, method = "selectNameByGoodsId")
    @Results({ 
        @Result(property = "attrName", column = "attrName"),
        @Result(property = "list", column = "attrName", 
        many = @Many(select = "com.szhengzhu.mapper.SpecificationInfoMapper.selectByName")) })
    List<SpecChooseBox> selectNameByGoodsId(@Param("goodsId") String goodsId);
}