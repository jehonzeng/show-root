package com.szhengzhu.schedule;

import com.szhengzhu.mapper.CommodityMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Component
public class CommoditySchedule {

    @Resource
    private CommodityMapper commodityMapper;
    
    @Scheduled(cron = "0 0 0 * * ?")
    public void autoCancelCommQuantity() {
        commodityMapper.cancelCommQuantity();
    }
}
