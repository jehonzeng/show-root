package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.base.AttributeInfo;
import com.szhengzhu.bean.vo.Combobox;

public interface AttributeInfoMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(AttributeInfo record);

    int insertSelective(AttributeInfo record);

    AttributeInfo selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(AttributeInfo record);

    int updateByPrimaryKey(AttributeInfo record);
    
    List<AttributeInfo> selectByExampleSelective(AttributeInfo attributeInfo);
    
    @Select("SELECT COUNT(mark_id) FROM t_attribute_info WHERE (`code`=#{code} OR `name`=#{name}) AND mark_id<>#{markId}")
    int countAttribute(@Param("code") String code, @Param("name") String name, @Param("markId") String markId);
    
    @Select("SELECT code, `name` FROM t_attribute_info WHERE server_status=1 AND type=#{type}")
    List<Combobox> selectCombobox(@Param("type") String type);
}    
