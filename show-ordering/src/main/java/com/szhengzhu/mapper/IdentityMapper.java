package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.szhengzhu.bean.ordering.Identity;
import com.szhengzhu.bean.vo.Combobox;

public interface IdentityMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(Identity record);

    int insertSelective(Identity record);

    Identity selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(Identity record);

    int updateByPrimaryKey(Identity record);
    
    List<Identity> selectByExampleSelective(Identity identity);
    
    @Update("update t_identity_info set status = #{status,jdbcType=INTEGER}, modify_time = NOW() where mark_id=#{identityId}")
    void updateStatus(@Param("identityId") String identityId, @Param("status") Integer status);
    
    @Select("SELECT authority FROM t_identity_info d LEFT JOIN t_identity_employee e ON d.mark_id=e.identity_id WHERE e.employee_id=#{employeeId} AND d.store_id=#{storeId}")
    String selectAuth(@Param("employeeId") String employeeId, @Param("storeId") String storeId);
    
    @Select("select mark_id as code, name from t_identity_info where store_id=#{storeId} and status = 1")
    List<Combobox> selectCombobox(@Param("storeId") String storeId);
}