package com.szhengzhu.print;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.szhengzhu.bean.ordering.Employee;
import com.szhengzhu.bean.ordering.Indent;
import com.szhengzhu.bean.ordering.PrinterInfo;
import com.szhengzhu.bean.ordering.PrinterLog;
import com.szhengzhu.bean.ordering.param.IncomeParam;
import com.szhengzhu.bean.ordering.print.*;
import com.szhengzhu.code.PrintStatus;
import com.szhengzhu.mapper.*;
import com.szhengzhu.redis.Redis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class PrintService {

    @Resource
    private Redis redis;

    @Resource
    private IndentMapper indentMapper;

    @Resource
    private EmployeeMapper employeeMapper;

    @Resource
    private PrinterLogMapper printerLogMapper;

    @Resource
    private PrinterInfoMapper printerInfoMapper;

    @Resource
    private PrinterItemMapper printerItemMapper;

    @Resource
    private IndentPayMapper indentPayMapper;

    /**
     * 下单或者换桌
     *
     * @param indentId
     * @param timeCodes
     * @param employeeId
     * @throws IOException
     */
    public List<String> orderOrChange(String indentId, String timeCodes, String employeeId, String tableCode, String storeId) {
        log.info("order-or-change:{}:{}:{}:{}", indentId, timeCodes, employeeId, tableCode);
        Employee employee = employeeMapper.selectByPrimaryKey(employeeId);
        List<PrinterInfo> printerList = printerInfoMapper.selectStorePrinterByCode(storeId, null);
        List<String> logList = createCustomerOrder(printerList, employee.getName(), indentId, timeCodes);
        printerList = printerList.stream().filter(printer -> !PrinterCode.PT07.code.equals(printer.getPrinterCode()) && !PrinterCode.PT09.code.equals(printer.getPrinterCode()) && !PrinterCode.PT08.code.equals(printer.getPrinterCode()))
                .collect(Collectors.toList());
        for (PrinterInfo printer : printerList) {
            List<PrintProduce> printProduceList = printerItemMapper.selectPrintProduce(indentId, printer.getMarkId(), timeCodes);
            PrintBase base;
            for (PrintProduce produce : printProduceList) {
                base = PrintBase.builder().printerCode(printer.getPrinterCode()).socket(printer.getPortName())
                        .portType(printer.getPortType()).deptName(printer.getDeptName()).tail(printer.getTail()).printer(employee.getName()).build();
                base.setPrinter(employee.getName());
                // 1点菜、2退菜、3换桌、4换菜
                if (StrUtil.isNotEmpty(tableCode)) {
                    // 换桌操作
                    produce.setOperate(ProduceOperate.CHANGE_TABLE.code);
                    produce.setTableCode(tableCode);
                } else {
                    produce.setOperate(ProduceOperate.ORDER_DISH.code);
                }
                base.setProduce(produce);
                String logId = savePrintLog(base, indentId);
                logList.add(logId);
            }
        }
        return logList;
    }

    /**
     * 生成客户单打印记录
     *
     * @param printerList
     * @param employeeName
     * @param indentId
     * @param timeCodes
     * @return
     */
    private List<String> createCustomerOrder(List<PrinterInfo> printerList, String employeeName, String indentId, String timeCodes) {
        List<String> logList = new LinkedList<>();
        List<PrinterInfo> printerListPt08 = printerList.stream().filter(printer -> PrinterCode.PT08.code.equals(printer.getPrinterCode())).collect(Collectors.toList());
        PrintBase base;
        for (PrinterInfo printer : printerListPt08) {
            base = PrintBase.builder().printerCode(printer.getPrinterCode()).socket(printer.getPortName())
                    .portType(printer.getPortType()).tail(printer.getTail()).printer(employeeName).build();
            PrintIndent printIndent = printerItemMapper.selectPrintIndent(indentId, printer.getMarkId(), timeCodes, 1);
            base.setIndent(printIndent);
            String logId = savePrintLog(base, indentId);
            logList.add(logId);
        }
        return logList;
    }

    /**
     * 保存打印记录
     */
    private String savePrintLog(PrintBase base, String indentId) {
        String tableId = null;
        if (StrUtil.isNotBlank(indentId)) {
            tableId = indentMapper.selectTableIdByIndentId(indentId);
        }
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        String logId = snowflake.nextIdStr();
        base.setPrintType(PrinterCode.getType(base.getPrinterCode()));
        PrinterLog log = PrinterLog.builder().markId(logId).tableId(tableId).indentId(indentId).printerCode(base.getPrinterCode())
                .printType(PrinterCode.getType(base.getPrinterCode())).sendTime(DateUtil.date()).statusCode(500)
                .errorInfo(PrintStatus.getValue(500)).printData(JSON.toJSONString(base)).build();
        printerLogMapper.insertSelective(log);
        redis.set("print:data:" + logId, JSON.toJSONString(base), 3 * 60L);
        return logId;
    }

    /**
     * 预览单或者结账单
     *
     * @param indentId
     * @param employeeId
     * @param printerCode
     * @throws IOException
     */
    public List<String> previewOrBill(String indentId, String employeeId, String printerCode) {
        log.info("preview-or-bill:{}:{}:{}", indentId, employeeId, printerCode);
        List<String> logList = new LinkedList<>();
        try {
            Indent indent = indentMapper.selectByPrimaryKey(indentId);
            Employee employee = employeeMapper.selectByPrimaryKey(employeeId);
            List<PrinterInfo> printerList = printerInfoMapper.selectStorePrinterByCode(indent.getStoreId(), printerCode);
            PrintBase base;
            PrintIndent printIndent;
            for (PrinterInfo printer : printerList) {
                base = PrintBase.builder().printerCode(printer.getPrinterCode()).socket(printer.getPortName())
                        .portType(printer.getPortType()).tail(printer.getTail()).printer(employee.getName()).build();
                printIndent = printerItemMapper.selectPrintIndent(indentId, printer.getMarkId(), null, null);
                if (ObjectUtil.isNull(printIndent.getDate())) {
                    printIndent.setDate(DateUtil.date());
                }
                log.info(String.valueOf(printIndent));
                base.setIndent(printIndent);
                String logId = savePrintLog(base, indentId);
                logList.add(logId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logList;
    }

    /**
     * 退菜
     *
     * @param indentId
     * @param employeeId
     * @param detailId
     */
    public List<String> returnDish(String indentId, String employeeId, String detailId) {
        List<String> logList = new LinkedList<>();
        try {
            log.info("return-dish:{}:{}:{}", indentId, employeeId, detailId);
            Employee employee = employeeMapper.selectByPrimaryKey(employeeId);
            List<PrintBase> list = printerItemMapper.selectReturnProduce(detailId);
            for (PrintBase base : list) {
                base.setPrinter(employee.getName());
                // 1点菜、2退菜、3换桌、4换菜
                base.getProduce().setOperate(ProduceOperate.RETURN_DISH.code);
                base.getProduce().setQuantity(1);
                String logId = savePrintLog(base, indentId);
                logList.add(logId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logList;
    }

    /**
     * 打印收入账单
     *
     * @param incomeParam
     * @return
     */
    public List<String> printIncome(IncomeParam incomeParam) {
        List<String> logList = new LinkedList<>();
        try {
            log.info("print:income:{}:{}", incomeParam.getStartDate(), incomeParam.getEndDate());
            Employee employee = employeeMapper.selectByPrimaryKey(incomeParam.getEmployeeId());
            PrintIncome income = indentMapper.selectIncome(incomeParam.getStartDate(), incomeParam.getEndDate(), incomeParam.getStoreId());
            List<PrintPay> payList = indentPayMapper.selectIncomePay(incomeParam.getStartDate(), incomeParam.getEndDate(), incomeParam.getStoreId());
            income.setPayList(payList);
            income.setStartDate(incomeParam.getStartDate());
            income.setEndDate(incomeParam.getEndDate());
            List<PrinterInfo> printerList = printerInfoMapper.selectStorePrinterByCode(incomeParam.getStoreId(), PrinterCode.PT06.code);
            PrintBase base;
            for (PrinterInfo printerInfo : printerList) {
                base = PrintBase.builder().printerCode(PrinterCode.PT06.code)
                        .printType(printerInfo.getPortType()).socket(printerInfo.getPortName())
                        .portType(printerInfo.getPortType()).printer(employee.getName())
                        .build();
                base.setIncome(income);
                String logId = savePrintLog(base, null);
                logList.add(logId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logList;
    }
}
