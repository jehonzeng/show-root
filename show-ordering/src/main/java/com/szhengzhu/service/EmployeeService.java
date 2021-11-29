package com.szhengzhu.service;

import com.szhengzhu.bean.ordering.Employee;
import com.szhengzhu.bean.ordering.param.EmployeeParam;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

public interface EmployeeService {

    /**
     * 获取员工分页列表
     *
     * @param pageParam
     * @return
     */
    PageGrid<Employee> page(PageParam<EmployeeParam> pageParam);

    /**
     * 获取员工详细信息
     *
     * @param employeeId
     * @return
     */
    Employee getInfo(String employeeId);

    /**
     * 添加员工信息
     *
     * @param employee
     * @return
     */
    String add(Employee employee);

    /**
     * 修改员工信息
     *
     * @param employee
     * @return
     */
    void modify(Employee employee);

    /**
     * 删除员工信息
     *
     * @param employeeId
     * @return
     */
    void delete(String employeeId);

    /**
     * @param phone
     * @return
     */
    Employee getByPhone(String phone);
}
