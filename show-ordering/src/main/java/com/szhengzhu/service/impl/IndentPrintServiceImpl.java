package com.szhengzhu.service.impl;

import com.szhengzhu.bean.ordering.param.IncomeParam;
import com.szhengzhu.bean.ordering.print.PrinterCode;
import com.szhengzhu.print.PrintService;
import com.szhengzhu.service.IndentPrintService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 */
@Service("indentPrintService")
public class IndentPrintServiceImpl implements IndentPrintService {

    @Resource
    private PrintService printService;

    @Override
    public List<String> resPrintIncome(IncomeParam incomeParam) {
        return printService.printIncome(incomeParam);
    }

    @Transactional
    @Override
    public List<String> resPrintPreview(String indentId, String employeeId) {
        return printService.previewOrBill(indentId, employeeId, PrinterCode.PT09.code);
    }

    @Override
    public List<String> resPrintBill(String indentId, String employeeId) {
        return printService.previewOrBill(indentId, employeeId, PrinterCode.PT07.code);
    }
}
