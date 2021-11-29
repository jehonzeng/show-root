package com.szhengzhu.controller;

import com.alibaba.fastjson.JSONObject;
import com.szhengzhu.bean.ordering.PrinterInfo;
import com.szhengzhu.bean.ordering.PrinterLog;
import com.szhengzhu.bean.ordering.param.PrintLogParam;
import com.szhengzhu.bean.ordering.vo.PrintLogVo;
import com.szhengzhu.bean.ordering.vo.PrinterCommodityVo;
import com.szhengzhu.bean.ordering.vo.PrinterVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.PrinterService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
@RequestMapping(value = "/printers")
public class PrinterController {

    @Resource
    private PrinterService printerService;

    @PostMapping(value = "/page")
    public PageGrid<PrinterVo> pagePrinter(@RequestBody PageParam<PrinterInfo> base) {
        return printerService.page(base);
    }

    @PostMapping(value = "/add")
    public void addPrinter(@RequestBody PrinterInfo base) {
        printerService.addPrinter(base);
    }

    @PatchMapping(value = "/modify")
    public void modifyPrinter(@RequestBody @Validated PrinterInfo base) {
        printerService.modifyPrinter(base);
    }

    @PatchMapping(value = "/batch/{status}")
    public void modifyStatus(@RequestBody @NotEmpty String[] printerIds, @PathVariable("status") @NotNull Integer status) {
        printerService.modifyStatus(printerIds, status);
    }

    @DeleteMapping(value = "/delete/{printerId}")
    public void deletePrinter(@PathVariable("printerId") @NotBlank String printerId) {
        printerService.deletePrinter(printerId);
    }

    @PostMapping(value = "/commodity/batch/{printerId}")
    public void addBatchCommodity(@RequestBody List<PrinterCommodityVo> commoditys, @PathVariable("printerId") @NotBlank String printerId) {
        printerService.addOrDelBatchCommodity(printerId, commoditys);
    }

    @PostMapping(value = "/log/page")
    public PageGrid<PrintLogVo> pageLog(@RequestBody PageParam<PrintLogParam> pageParam) {
        return printerService.pageLog(pageParam);
    }

    @PatchMapping(value = "/log/batch/modify")
    public void modifyBatchLog(@RequestBody @NotEmpty List<PrinterLog> list) {
        printerService.modifyBatchLog(list);
    }

    @PatchMapping(value = "/log/modify")
    public void modifyLog(@RequestBody PrinterLog log) {
        printerService.modifyLog(log);
    }

    @GetMapping(value = "/log/print/data/{logId}")
    public JSONObject getLogData(@PathVariable("logId") @NotBlank String logId) {
        return printerService.getLogData(logId);
    }

    @GetMapping(value = "/log/print/info/{logId}")
    public List<String> getLogInfo(@PathVariable("logId") @NotBlank String logId) {
        return printerService.getLogInfo(logId);
    }
}
