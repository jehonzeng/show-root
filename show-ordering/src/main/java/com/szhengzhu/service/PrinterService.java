package com.szhengzhu.service;

import com.alibaba.fastjson.JSONObject;
import com.szhengzhu.bean.ordering.PrinterInfo;
import com.szhengzhu.bean.ordering.PrinterLog;
import com.szhengzhu.bean.ordering.param.PrintLogParam;
import com.szhengzhu.bean.ordering.vo.PrintLogVo;
import com.szhengzhu.bean.ordering.vo.PrinterCommodityVo;
import com.szhengzhu.bean.ordering.vo.PrinterVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

public interface PrinterService {

    /**
     * 打印设备分页信息
     *
     * @param base
     * @return
     */
    PageGrid<PrinterVo> page(PageParam<PrinterInfo> base);

    /**
     * 添加打印设备信息
     *
     * @param base
     * @return
     */
    void addPrinter(PrinterInfo base);

    /**
     * 修改大打印设备信息
     *
     * @param base
     * @return
     */
    void modifyPrinter(PrinterInfo base);

    /**
     * 批量修改打印设备状态
     *
     * @param printerIds
     * @param status
     * @return
     */
    void modifyStatus(String[] printerIds, Integer status);

    /**
     * 批量添加或者删除打印设备对应的商品
     *
     * @param printerId
     * @return
     */
//    ?> addOrDelBatchCommodity(String printerId, String[] commodityIds);

    void addOrDelBatchCommodity(String printerId, List<PrinterCommodityVo> commoditys);

    /**
     * 删除打印设备（修改状态为-1：删除）
     *
     * @param printerId
     * @return
     */
    void deletePrinter(String printerId);

    PageGrid<PrintLogVo> pageLog(PageParam<PrintLogParam> pageParam);

    void modifyBatchLog(List<PrinterLog> list);

    void modifyLog(PrinterLog log);

    JSONObject getLogData(String logId);

    List<String> getLogInfo(String logId);
}
