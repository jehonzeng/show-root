package com.szhengzhu.schedule;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.szhengzhu.client.ShowGoodsClient;

@Component
public class TodayPurchase {
    
    @Resource
    private ShowGoodsClient showGoodsClient;
    
    
    @Scheduled(cron = "0 05 16 * * ?")
    public void autoCreatePurchase() {
        //生成今日采购单
        showGoodsClient.createPurchaseOrder();
    }
}
