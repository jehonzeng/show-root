package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.ordering.PrinterInfo;
import com.szhengzhu.bean.ordering.PrinterLog;
import com.szhengzhu.bean.ordering.param.PrintLogParam;
import com.szhengzhu.bean.ordering.vo.PrintLogVo;
import com.szhengzhu.bean.ordering.vo.PrinterCommodityVo;
import com.szhengzhu.bean.ordering.vo.PrinterVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.mapper.PrinterInfoMapper;
import com.szhengzhu.mapper.PrinterItemMapper;
import com.szhengzhu.mapper.PrinterLogMapper;
import com.szhengzhu.redis.Redis;
import com.szhengzhu.service.PrinterService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Administrator
 */
@Service("printerService")
public class PrinterServiceImpl implements PrinterService {

    @Resource
    private Redis redis;

    @Resource
    private PrinterLogMapper printerLogMapper;

    @Resource
    private PrinterInfoMapper printerInfoMapper;

    @Resource
    private PrinterItemMapper printerItemMapper;

    @Override
    public PageGrid<PrinterVo> page(PageParam<PrinterInfo> base) {
        PageMethod.startPage(base.getPageIndex(), base.getPageSize());
        PageMethod.orderBy(("mark_id".equals(base.getSidx()) ? "create_time" : base.getSidx()) + " "
                + base.getSort());
        List<PrinterVo> list = printerInfoMapper.selectByExampleSelective(base.getData());
        for (PrinterVo printer : list) {
            List<String> commodityList = printerItemMapper.selectCommodityByPrinterId(printer.getMarkId());
            printer.setCommodityList(commodityList.toArray(new String[0]));
        }
        PageInfo<PrinterVo> pageInfo = new PageInfo<>(list);
        return new PageGrid<>(pageInfo);
    }

    @Override
    public void addPrinter(PrinterInfo base) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        base.setMarkId(snowflake.nextIdStr());
        base.setCreateTime(DateUtil.date());
        printerInfoMapper.insertSelective(base);
    }

    @Override
    public void modifyPrinter(PrinterInfo base) {
        base.setModifyTime(DateUtil.date());
        printerInfoMapper.updateByPrimaryKeySelective(base);
    }

    @Override
    public void modifyStatus(String[] printerIds, Integer status) {
        printerInfoMapper.updateBatchStatus(printerIds, status);
    }

    @Override
    public void addOrDelBatchCommodity(String printerId, List<PrinterCommodityVo> commoditys) {
        printerItemMapper.deleteByPrinterId(printerId);
        if (!commoditys.isEmpty()) { printerItemMapper.insertBatchCommodity(printerId, commoditys); }
    }

    @Override
    public void deletePrinter(String printerId) {
        printerInfoMapper.updatePrinterById(printerId, -1);
    }

    @Override
    public PageGrid<PrintLogVo> pageLog(PageParam<PrintLogParam> pageParam) {
        PageMethod.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        PageMethod.orderBy(("mark_id".equals(pageParam.getSidx()) ? "send_time" : pageParam.getSidx()) + " "
                + pageParam.getSort());
        List<PrintLogVo> list = printerLogMapper.selectManageList(pageParam.getData());
        PageInfo<PrintLogVo> pageInfo = new PageInfo<>(list);
        return new PageGrid<>(pageInfo);
    }

    @Override
    public void modifyBatchLog(List<PrinterLog> list) {
        for (PrinterLog printerLog : list) { printerLogMapper.updateByPrimaryKeySelective(printerLog); }
        
    }

    @Override
    public void modifyLog(PrinterLog log) {
        printerLogMapper.updateByPrimaryKeySelective(log);
        
    }

    @Override
    public JSONObject getLogData(String logId) {
        String data = printerLogMapper.selectPrintData(logId);
        return JSON.parseObject(data);
    }

    @Override
    public List<String> getLogInfo(String logId) {
        PrinterLog log = printerLogMapper.selectByPrimaryKey(logId);
        redis.set("print:data:" + logId, log.getPrintData(), 3 * 60L);
        List<String> logList = new LinkedList<>();
        logList.add(logId);
        return logList;
    }

}
