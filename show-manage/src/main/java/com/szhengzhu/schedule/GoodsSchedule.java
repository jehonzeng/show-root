package com.szhengzhu.schedule;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.szhengzhu.client.ShowGoodsClient;

@Component
public class GoodsSchedule {

    @Resource
    private ShowGoodsClient showGoodsClient;

    /**
     * 已经设置时间的商品，改变商品状态
     *
     * @date 2019年6月28日
     */
    @Scheduled(cron = "0 * * * * ?")
    public void autoUpdateGoodsStatus() {
        //根据商品定时判断更改状态
    }
}
