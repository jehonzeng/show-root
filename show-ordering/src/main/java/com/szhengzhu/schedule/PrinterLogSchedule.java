package com.szhengzhu.schedule;

import com.szhengzhu.mapper.PrinterLogMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Component
public class PrinterLogSchedule {

    @Resource
    private PrinterLogMapper printerLogMapper;
    
    @Scheduled(cron = "0 0 3 * * ?")
    public void autoExpire() {
        printerLogMapper.deleteOldLog();
    }
}
