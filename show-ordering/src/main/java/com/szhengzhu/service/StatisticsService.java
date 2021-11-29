package com.szhengzhu.service;

import com.szhengzhu.bean.ordering.*;
import com.szhengzhu.bean.ordering.param.TableInfoParam;
import com.szhengzhu.bean.ordering.param.TableUseParam;

import java.util.List;
import java.util.Map;

public interface StatisticsService {
    /**
     * 根据时间段查询营收数据
     *
     * @param info
     * @return
     */
    List<TableInfoParam> tableInfo(TableInfo info);

    /**
     * 根据时间查询营收数据
     *
     * @param info
     * @return
     */
    List<TableUseParam> tableByTime(TableInfo info);

    /**
     * 收入详细信息
     *
     * @param income
     * @return
     */
    Map<String, Object> payIncome(Income income);

    /**
     * 分类收入详细信息
     *
     * @param income
     * @return
     */
    List<IncomeByType> type(Income income);

    /**
     * 收支对比
     * @param tableInfo
     * @return
     */
    List<AmountCompare> amountCompare(TableInfo tableInfo);
}
