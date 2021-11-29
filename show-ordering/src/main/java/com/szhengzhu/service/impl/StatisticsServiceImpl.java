package com.szhengzhu.service.impl;

import com.szhengzhu.bean.ordering.*;
import com.szhengzhu.bean.ordering.param.TableInfoParam;
import com.szhengzhu.bean.ordering.param.TableUseParam;
import com.szhengzhu.bean.ordering.print.PrintIncome;
import com.szhengzhu.mapper.StatisticsMapper;
import com.szhengzhu.service.StatisticsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Service("statisticsService")
public class StatisticsServiceImpl implements StatisticsService {
    @Resource
    private StatisticsMapper statisticsMapper;

    @Override
    public List<TableUseParam> tableByTime(TableInfo info) {
        if ("1".equals(String.valueOf(info.getNum()))) {
            return statisticsMapper.tableCls(info);
        } else if ("2".equals(String.valueOf(info.getNum()))) {
            return statisticsMapper.tableArea(info);
        }
        return new ArrayList<>();
    }

    @Override
    public List<TableInfoParam> tableInfo(TableInfo info) {
        if ("1".equals(String.valueOf(info.getNum()))) {
            return statisticsMapper.tableClsInfo(info);
        } else if ("2".equals(String.valueOf(info.getNum()))) {
            return statisticsMapper.tableAreaInfo(info);
        }
        return new ArrayList<>();
    }

    @Override
    public Map<String, Object> payIncome(Income income) {
        income.setReceived(true);
        PrintIncome printIncome = statisticsMapper.incomeByType(income);
        printIncome.setPayList(statisticsMapper.incomePayByType(income));
        printIncome.setStartDate(income.getBeginDate());
        printIncome.setEndDate(income.getEndDate());
        income.setReceived(false);
        PrintIncome printIncome1 = statisticsMapper.incomeByType(income);
        printIncome1.setPayList(statisticsMapper.incomePayByType(income));
        printIncome1.setStartDate(income.getBeginDate());
        printIncome1.setEndDate(income.getEndDate());
        Map<String, Object> map = new HashMap<>();
        map.put("income", statisticsMapper.income(income));
        map.put("received", printIncome);
        map.put("not_Received", printIncome1);
        return map;
    }

    public List<IncomeByType> type(Income income){
        return statisticsMapper.type(income);
    }

    public List<AmountCompare> amountCompare(TableInfo tableInfo){
        return statisticsMapper.amountCompare(tableInfo);
    }
}
