package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.ordering.Employee;
import com.szhengzhu.bean.ordering.StoreEmployee;
import com.szhengzhu.bean.ordering.param.EmployeeParam;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.mapper.EmployeeMapper;
import com.szhengzhu.mapper.IdentityEmployeeMapper;
import com.szhengzhu.mapper.StoreEmployeeMapper;
import com.szhengzhu.service.EmployeeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

    @Resource
    private EmployeeMapper employeeMapper;

    @Resource
    private IdentityEmployeeMapper identityEmployeeMapper;

    @Resource
    private StoreEmployeeMapper storeEmployeeMapper;

    @Override
    public PageGrid<Employee> page(PageParam<EmployeeParam> pageParam) {
        String sidx = pageParam.getSidx().equals("mark_id") ? "create_time " : pageParam.getSidx();
        PageMethod.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        PageMethod.orderBy(sidx + " " + pageParam.getSort());
        PageInfo<Employee> pageInfo = new PageInfo<>(
                employeeMapper.selectByExampleSelective(pageParam.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public Employee getInfo(String employeeId) {
        return employeeMapper.selectByPrimaryKey(employeeId);
    }

    @Override
    public String add(Employee employee) {
        Employee emp = employeeMapper.selectByPhone(employee.getPhone());
        ShowAssert.checkTrue(emp != null, StatusCode._5021);
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        String markId = snowflake.nextIdStr();
        employee.setMarkId(markId);
        employee.setCreateTime(DateUtil.date());
        employeeMapper.insertSelective(employee);
        StoreEmployee storeEmployee = new StoreEmployee();
        storeEmployee.setEmployeeId(markId);
        storeEmployee.setStoreId(employee.getStoreId());
        storeEmployeeMapper.insert(storeEmployee);
        if (!StrUtil.isEmpty(employee.getIdentityId())) {
            identityEmployeeMapper.insertSelective(markId, employee.getIdentityId());
        }
        return markId;
    }

    @Override
    public void modify(Employee employee) {
        Employee emp = employeeMapper.selectByPhone(employee.getPhone());
        ShowAssert.checkTrue(emp != null && !emp.getMarkId().equals(employee.getMarkId()), StatusCode._5021);
        employee.setModifyTime(DateUtil.date());
        employeeMapper.updateByPrimaryKeySelective(employee);
        identityEmployeeMapper.deleteStoreEmployee(employee.getMarkId());
        if (!StrUtil.isEmpty(employee.getIdentityId()) && !employee.getIdentityId().equals("1")) {
            identityEmployeeMapper.insertSelective(employee.getMarkId(), employee.getIdentityId());
        }
    }

    @Override
    public void delete(String employeeId) {
        employeeMapper.updateStatus(employeeId, -1);
    }

    @Override
    public Employee getByPhone(String phone) {
        return employeeMapper.selectByPhone(phone);
    }
}
