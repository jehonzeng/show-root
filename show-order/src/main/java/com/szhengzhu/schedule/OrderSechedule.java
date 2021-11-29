package com.szhengzhu.schedule;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.szhengzhu.mapper.OrderInfoMapper;

/**
 * @author Jehon Zeng
 */
@Component
public class OrderSechedule {

    @Resource
    private OrderInfoMapper orderInfoMapper;

    /**
     * 订单自动签收
     * 
     * @date 2019年9月6日 下午5:52:45
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void autoReceive() {
        orderInfoMapper.updateOrderReceive();
    }

    /**
     * 自动合团 1.获取正在拼团的商品列表，server_status = 1 and end_time -当前时间 < 5分钟 and father is
     * null group by 商品 1.1.获取该团活动对应的主团单 father为空 1.1.1 确认该团单为子团单，则跳过该次循环 1.1.2
     * 团单人数已满员 则将团单或团单与子团单状态设置为拼单成功 状态值为2 1.1.3 结束时间倒序查询出第一个团单离结束时间超过五分钟，则结束本次循环
     * 
     * @date 2019年3月26日 下午12:08:34
     */
    @Scheduled(fixedDelay = 1000 * 60 * 3)
    public void autoCombination() {
//        System.out.println(1111);
    }
}
