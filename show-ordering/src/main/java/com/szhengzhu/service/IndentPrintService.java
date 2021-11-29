package com.szhengzhu.service;

import com.szhengzhu.bean.ordering.param.IncomeParam;

import java.util.List;

public interface IndentPrintService {

    /**
     * 打印收入总单
     *
     * @return
     */
    List<String> resPrintIncome(IncomeParam incomeParam);


    /**
     * 获取打印预结单信息
     *
     * @param indentId
     * @param employeeId
     * @return
     */
    List<String> resPrintPreview(String indentId, String employeeId);

    /**
     * 获取结账单打印信息
     *
     * @param indentId
     * @param employeeId
     * @return
     */
    List<String> resPrintBill(String indentId, String employeeId);
}
