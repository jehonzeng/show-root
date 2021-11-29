package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.szhengzhu.bean.ordering.IdentityEmployee;

public interface IdentityEmployeeMapper {
    
    int deleteByPrimaryKey(IdentityEmployee key);

    int insert(IdentityEmployee record);

    int insertSelective(@Param("employeeId") String employeeId, @Param("identityId") String identityId);
    
    int insertBatch(@Param("employeeIds") String[] employeeIds, @Param("identityId") String identityId);
    
    @Delete("delete from t_identity_employee where identity_id = #{identityId}")
    int deleteByIdentityId(@Param("identityId") String identityId);
    
    @Select("select employee_id from t_identity_employee where identity_id = #{identityId}")
    List<String> selectByIdentityId(@Param("identityId") String identityId);
    
    @Delete("delete from t_identity_employee where employee_id=#{employeeId} and identity_id in (select mark_id from t_identity_info i left join t_store_employee e on e.store_id=i.store_id where employee_id=#{employeeId})")
    int deleteStoreEmployee(@Param("employeeId") String employeeId);
}
