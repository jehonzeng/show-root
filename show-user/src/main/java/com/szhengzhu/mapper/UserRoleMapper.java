package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.szhengzhu.bean.user.UserRole;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.UserBase;
import com.szhengzhu.provider.UserProvider;

public interface UserRoleMapper {
    
    int insert(UserRole record);
    
    int insertRoleUsers(@Param("roleId") String roleId, @Param("userIds") String[] userIds);
    
    int deleteRoleUsers(@Param("roleId") String roleId, @Param("userIds") String[] userIds);
    
    @SelectProvider(type = UserProvider.class,method = "selectListByCode")
    List<Combobox> selectListByCode(@Param("roleCode") String roleCode);

    @SelectProvider(type = UserProvider.class,method = "selectUsersByRoleId")
    List<UserBase> selectUsersByRoleId(@Param("roleId") String roleId);
}