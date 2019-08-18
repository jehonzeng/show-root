package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.user.RoleInfo;

public interface RoleInfoMapper {

    int deleteByPrimaryKey(Long markId);

    int insert(RoleInfo record);

    int insertSelective(RoleInfo record);

    RoleInfo selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(RoleInfo record);

    int updateByPrimaryKey(RoleInfo record);

    List<RoleInfo> selectByExampleSelective(RoleInfo roleInfo);

    @Select("SELECT count(mark_id) FROM t_role_info WHERE role_name = #{roleName} AND mark_id <> #{markId}")
    int countRole(@Param("roleName") String roleName, @Param("markId") String markId);
}