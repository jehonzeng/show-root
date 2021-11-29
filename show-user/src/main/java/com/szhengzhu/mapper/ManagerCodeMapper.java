package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.user.ManagerCode;

public interface ManagerCodeMapper {
	
    int deleteByPrimaryKey(String markId);

    int insert(ManagerCode record);

    int insertSelective(ManagerCode record);

    ManagerCode selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(ManagerCode record);

    int updateByPrimaryKey(ManagerCode record);
    
    List<ManagerCode> selectByExampleSelective(ManagerCode managerCode);
    
    int insertBatch(List<ManagerCode> items);
    
    @Select("select mark_id as markId, user_id as userId, code, discount, server_status as serverStatus from t_manager_code where code = #{code} and server_status=1 limit 1")
    ManagerCode selectByCode(@Param("code") String code);
}