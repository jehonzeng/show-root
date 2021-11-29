package com.szhengzhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.szhengzhu.bean.ordering.Employee;
import com.szhengzhu.bean.ordering.param.EmployeeParam;

public interface EmployeeMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(Employee record);

    int insertSelective(Employee record);

    Employee selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);
    
    List<Employee> selectByExampleSelective(EmployeeParam employeeParam);
    
    @Update("update t_employee_info set status=#{status}, modify_time=NOW() where mark_id=#{employeeId}")
    int updateStatus(@Param("employeeId") String employeeId, @Param("status") Integer status);
    
    Employee selectByPhone(@Param("phone") String phone);
}