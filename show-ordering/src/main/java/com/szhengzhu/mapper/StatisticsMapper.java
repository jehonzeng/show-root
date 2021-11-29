package com.szhengzhu.mapper;

import com.szhengzhu.bean.ordering.*;
import com.szhengzhu.bean.ordering.param.TableUseParam;
import com.szhengzhu.bean.ordering.param.TableInfoParam;
import com.szhengzhu.bean.ordering.print.PrintIncome;
import com.szhengzhu.bean.ordering.print.PrintPay;

import java.util.List;
import java.util.Map;

public interface StatisticsMapper {
    /**
     * 查询餐桌类型营收
     * @param info
     * @return
     */
    List<TableInfoParam> tableClsInfo(TableInfo info);

    /**
     * 查询餐桌区域营收
     * @param info
     * @return
     */
    List<TableInfoParam> tableAreaInfo(TableInfo info);

    /**
     * 分时间段查询餐桌类型营收
     * @param info
     * @return
     */
    List<TableUseParam> tableCls(TableInfo info);

    /**
     * 分时间段查询餐桌区域营收
     * @param info
     * @return
     */
    List<TableUseParam> tableArea(TableInfo info);

    /**
     * 实收和营收总收入
     * @param income
     * @return
     */
    Map<String,Object> income(Income income);

    /**
     * 实收明细总额
     * @param income
     * @return
     */
    PrintIncome incomeByType(Income income);

    /**
     * 实收明细
     * @param income
     * @return
     */
    List<PrintPay> incomePayByType(Income income);

    List<IncomeByType> type(Income income);

    List<AmountCompare> amountCompare(TableInfo tableInfo);
}
