package com.szhengzhu.schedule;

import com.szhengzhu.feign.ShowGoodsClient;
import com.szhengzhu.core.Result;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class PurchaseSchedule {
    
    @Resource
    private ShowGoodsClient showGoodsClient;
    
    
    @Scheduled(cron = "0 0 8 * * ?")
    public void autoCreatePurchase() {
        //生成今日采购单
        Result<?> result = showGoodsClient.createPurchaseOrder();
        if(result.isSuccess()) {
            // 推送告知生成采购单信息
            System.out.println("采购单已经生成");
        }
    }
}
