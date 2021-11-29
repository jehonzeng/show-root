package com.szhengzhu.controller;

import com.szhengzhu.bean.ordering.Employee;
import com.szhengzhu.bean.ordering.param.EmployeeParam;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.EmployeeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;

    @PostMapping(value = "/page")
    public PageGrid<Employee> page(@RequestBody PageParam<EmployeeParam> pageParam) {
        return employeeService.page(pageParam);
    }

    @GetMapping(value = "/{employeeId}")
    public Employee getInfo(@PathVariable("employeeId") @NotBlank String employeeId) {
        return employeeService.getInfo(employeeId);
    }

    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody @Validated Employee employee) {
        return new Result<>(employeeService.add(employee));
    }

    @PatchMapping(value = "/modify")
    public void modify(@RequestBody @Validated Employee employee) {
        employeeService.modify(employee);
    }

    @DeleteMapping(value = "/{employeeId}")
    public void delete(@PathVariable("employeeId") @NotBlank String employeeId) {
        employeeService.delete(employeeId);
    }

    @GetMapping(value = "/p/{phone}")
    public Employee getInfoByPhone(@PathVariable("phone") @NotBlank String phone) {
        return employeeService.getByPhone(phone);
    }
}
