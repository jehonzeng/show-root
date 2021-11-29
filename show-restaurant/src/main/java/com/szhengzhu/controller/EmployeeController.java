package com.szhengzhu.controller;

import cn.hutool.core.util.ObjectUtil;
import com.szhengzhu.client.ShowOrderingClient;
import com.szhengzhu.bean.ordering.Employee;
import com.szhengzhu.bean.ordering.param.EmployeeParam;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

/**
 * @author Administrator
 */
@Validated
@Api(tags = "员工：EmployeeController")
@RestController
@RequestMapping("/v1/employee")
public class EmployeeController {

    @Resource
    private ShowOrderingClient showOrderingClient;

    @ApiOperation(value = "获取员工分页列表")
    @PostMapping(value = "/page")
    public Result<PageGrid<Employee>> page(HttpServletRequest req, @RequestBody PageParam<EmployeeParam> pageParam) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        EmployeeParam param = ObjectUtil.isNull(pageParam.getData()) ? new EmployeeParam() : pageParam.getData();
        param.setStoreId(storeId);
        pageParam.setData(param);
        return showOrderingClient.pageEmployee(pageParam);
    }

    @ApiOperation(value = "获取员工详细信息")
    @GetMapping(value = "/{employeeId}")
    public Result<Employee> getInfo(@PathVariable("employeeId") @NotBlank String employeeId) {
        return showOrderingClient.getEmployeeInfo(employeeId);
    }

    @ApiOperation(value = "添加员工", notes = "gender: 1、男  2、女")
    @PostMapping(value = "")
    public Result<String> add(HttpServletRequest req, @RequestBody @Validated Employee employee) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        employee.setStoreId(storeId);
        return showOrderingClient.addEmployee(employee);
    }

    @ApiOperation(value = "修改员工")
    @PatchMapping(value = "")
    public Result modify(@RequestBody @Validated Employee employee) {
        return showOrderingClient.modifyEmployee(employee);
    }

    @ApiOperation(value = "删除员工")
    @DeleteMapping(value = "/{employeeId}")
    public Result delete(@PathVariable("employeeId") @NotBlank String employeeId) {
        return showOrderingClient.deleteEmployee(employeeId);
    }
}
