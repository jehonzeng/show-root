package com.szhengzhu.controller;

import com.szhengzhu.bean.ordering.*;
import com.szhengzhu.bean.ordering.param.TableInfoParam;
import com.szhengzhu.bean.ordering.param.TableUseParam;
import com.szhengzhu.service.StatisticsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    @Resource
    private StatisticsService statisticsService;

    @PostMapping(value = "/table/info")
    public List<TableInfoParam> tableInfo(@RequestBody @Validated TableInfo info) {
        return statisticsService.tableInfo(info);
    }

    @PostMapping(value = "/table/ByTime")
    public List<TableUseParam> tableByTime(@RequestBody @Validated TableInfo info) {
        return statisticsService.tableByTime(info);
    }

    @PostMapping(value = "/pay/income")
    public Map<String, Object> payIncome(@RequestBody Income income) {
        return statisticsService.payIncome(income);
    }

    @PostMapping(value = "/pay/incomeByType")
    public List<IncomeByType> type(@RequestBody Income income) {
        return statisticsService.type(income);
    }

    @PostMapping(value = "/amount/compare")
    public List<AmountCompare> amountCompare(@RequestBody TableInfo tableInfo) {
        return statisticsService.amountCompare(tableInfo);
    }
}
