package com.szhengzhu.controller;

import com.szhengzhu.client.ShowOrderingClient;
import com.szhengzhu.bean.ordering.AmountCompare;
import com.szhengzhu.bean.ordering.Income;
import com.szhengzhu.bean.ordering.IncomeByType;
import com.szhengzhu.bean.ordering.TableInfo;
import com.szhengzhu.bean.ordering.param.TableInfoParam;
import com.szhengzhu.bean.ordering.param.TableUseParam;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Api(tags = "统计分析：StatisticsController")
@RestController
@RequestMapping("/v1/statistics")
public class StatisticsController {
    @Resource
    private ShowOrderingClient showOrderingClient;

    @ApiOperation("根据时间段查询桌台营收数据(餐桌类型，餐桌区域)")
    @PostMapping(value = "/table/info")
    public Result<List<TableInfoParam>> tableInfo(HttpServletRequest req, @RequestBody TableInfo info) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        info.setStoreId(storeId);
        return showOrderingClient.tableInfo(info);
    }

    @ApiOperation("根据时间查询桌台营收数据(餐桌类型，餐桌区域)")
    @PostMapping(value = "/table/ByTime")
    public Result<List<TableUseParam>> tableByTime(HttpServletRequest req, @RequestBody TableInfo info) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        info.setStoreId(storeId);
        return showOrderingClient.tableByTime(info);
    }

    @ApiOperation("根据时间段查询收入详细情况")
    @PostMapping(value = "/pay/income")
    Result<Map<String, Object>> payIncome(HttpSession session, HttpServletRequest req, @RequestBody Income income) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        String employeeId = (String) session.getAttribute(Contacts.RESTAURANT_USER);
        Result<String> authResult = showOrderingClient.getIdentityAuth(employeeId, storeId);
        if (authResult.getData().equals("cashier")) {
            income.setEmployeeId(employeeId);
        }
        income.setStoreId(storeId);
        return showOrderingClient.payIncome(income);
    }

    @ApiOperation("查询收入详细情况")
    @PostMapping(value = "/pay/incomeByType")
    public Result<List<IncomeByType>> type(HttpServletRequest req, @RequestBody Income income){
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        income.setStoreId(storeId);
        return showOrderingClient.type(income);
    }

    @ApiOperation("查询收入对比")
    @PostMapping(value = "/amount/compare")
    public Result<List<AmountCompare>> amountCompare(HttpServletRequest req,@RequestBody TableInfo tableInfo){
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        tableInfo.setStoreId(storeId);
        return showOrderingClient.amountCompare(tableInfo);
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        //转换日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
