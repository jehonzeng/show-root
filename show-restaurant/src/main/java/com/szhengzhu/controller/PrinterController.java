package com.szhengzhu.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.szhengzhu.bean.ordering.PrinterInfo;
import com.szhengzhu.bean.ordering.PrinterLog;
import com.szhengzhu.bean.ordering.param.PrintLogParam;
import com.szhengzhu.bean.ordering.vo.PrintLogVo;
import com.szhengzhu.bean.ordering.vo.PrinterCommodityVo;
import com.szhengzhu.bean.ordering.vo.PrinterVo;
import com.szhengzhu.feign.ShowOrderingClient;
import com.szhengzhu.core.*;
import com.szhengzhu.exception.ShowAssert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author Administrator
 */
@Validated
@Api(tags = { "打印：PrinterController" })
@RestController
@RequestMapping(value = "/v1/printers")
public class PrinterController {

    @Resource
    private ShowOrderingClient showOrderingClient;

    @ApiOperation(value = "添加打印设备信息")
    @PostMapping(value = "")
    public Result addPrinter(HttpServletRequest request, @RequestBody @Validated PrinterInfo base) {
        String storeId = (String) request.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        base.setStoreId(storeId);
        return showOrderingClient.addPrinter(base);
    }

    @ApiOperation(value = "获取打印设备信息分页列表")
    @PostMapping(value = "/page")
    public Result<PageGrid<PrinterVo>> pagePrinter(HttpServletRequest request, @RequestBody PageParam<PrinterInfo> base) {
        String storeId = (String) request.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        PrinterInfo param = ObjectUtil.isNull(base.getData()) ? new PrinterInfo() : base.getData();
        param.setStoreId(storeId);
        base.setData(param);
        return showOrderingClient.pagePrinter(base);
    }

    @ApiOperation(value = "修改打印设备信息")
    @PatchMapping(value = "")
    public Result modifyPrinter(@RequestBody @Validated PrinterInfo base) {
        return showOrderingClient.modifyPrinter(base);
    }

    @ApiOperation(value = "批量修改打印设备状态(0：未启用 ，1：启用)")
    @PatchMapping(value = "/batch/enabled/{status}")
    public Result modifyStatus(@RequestBody @NotEmpty String[] printerIds, @PathVariable("status") @Min(-1) @Max(1) Integer status) {
        return showOrderingClient.modifyPrinterStatus(printerIds, status);
    }

    @ApiOperation(value = "批量删除打印设备(-1：删除)")
    @PatchMapping(value = "/batch/delete")
    public Result deletePrinter(@RequestBody @NotEmpty String[] printerIds) {
        return showOrderingClient.modifyPrinterStatus(printerIds, -1);
    }

    @ApiOperation(value = "删除打印信息(-1：删除)")
    @DeleteMapping(value = "/{printerId}")
    public Result deletePrinter(@PathVariable("printerId") @NotBlank String printerId) {
        return showOrderingClient.deletePrinter(printerId);
    }

//    @ApiOperation(value = "批量添加或删除打印商品")
//    @PostMapping(value = "/commodity/batch/{printerId}")
//    public Result<Object> addOrDelBatchCommodity(@RequestBody String[] commodityIds, @PathVariable("printerId") String printerId) {
//        if(StringUtils.isEmpty(printerId)) {
//            return new Result<>(StatusCode._4004);
//        }
//        return showOrderingClient.addOrDelBatchCommodity(commodityIds, printerId);
//    }

    @ApiOperation(value = "批量添加或删除打印商品")
    @PostMapping(value = "/commodity/batch/{printerId}")
    public Result addOrDelBatchCommodity(@RequestBody List<PrinterCommodityVo> commoditys,
            @PathVariable("printerId") String printerId) {
        ShowAssert.checkTrue(StrUtil.isEmpty(printerId), StatusCode._4004);
        return showOrderingClient.addOrDelBatchCommodity(commoditys, printerId);
    }

    @ApiOperation(value = "获取打印机监控信息分页列表")
    @PostMapping(value = "/log/page")
    public Result<PageGrid<PrintLogVo>> pageLog(HttpServletRequest req,
            @RequestBody PageParam<PrintLogParam> pageParam) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        PrintLogParam param = ObjectUtil.isNull(pageParam.getData()) ? new PrintLogParam() : pageParam.getData();
        param.setStoreId(storeId);
        pageParam.setData(param);
        return showOrderingClient.pagePrintLog(pageParam);
    }

    @ApiOperation(value = "打印机批量回写打印记录")
    @PatchMapping(value = "/log/batch/modify")
    public Result<Object> modifyBatchLog(@RequestBody List<PrinterLog> list) {
        return showOrderingClient.modifyBatchPrintLog(list);
    }

    @ApiOperation(value = "修改打印记录")
    @PatchMapping(value = "/log/modify")
    public Result modifyLog(@RequestBody PrinterLog log) {
        return showOrderingClient.modifyPrintLog(log);
    }

    @ApiOperation(value = "打印监控查看明细")
    @GetMapping(value = "/log/print/data/{logId}")
    public Result<JSONObject> getLogData(@PathVariable("logId") @NotBlank String logId) {
        return showOrderingClient.getPrintLogData(logId);
    }

    @ApiOperation(value = "打印监控重新打印")
    @GetMapping(value = "/log/print/info/{logId}")
    public Result getLogInfo(@PathVariable("logId") @NotBlank String logId) {
        return showOrderingClient.getPrintLogInfo(logId);
    }
}
