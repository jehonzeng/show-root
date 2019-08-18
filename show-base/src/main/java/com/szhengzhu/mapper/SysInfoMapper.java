package com.szhengzhu.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.base.SysInfo;

public interface SysInfoMapper {
    
    int deleteByPrimaryKey(String name);

    int insert(SysInfo record);

    String selectByName(String name);

    int updateByPrimaryKey(SysInfo record);
    
    @Select("SELECT COUNT(*) FROM t_sys_info WHERE name=#{name}")
    int countByName(@Param("name") String name);
}