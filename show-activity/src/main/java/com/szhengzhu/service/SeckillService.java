package com.szhengzhu.service;

import com.szhengzhu.bean.activity.SeckillInfo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

public interface SeckillService {

    /**
     * 获取秒杀活动分页列表
     * 
     * @date 2019年3月13日 下午3:01:42
     * @param seckillPage
     * @return
     */
    Result<PageGrid<SeckillInfo>> pageSeckill(PageParam<SeckillInfo> seckillPage);
    
    /**
     * 添加秒杀活动
     * 
     * @date 2019年3月13日 下午3:02:31
     * @param seckillInfo
     * @return
     */
    Result<SeckillInfo> saveSeckill(SeckillInfo seckillInfo);
    
    /**
     * 修改秒杀活动
     * 
     * @date 2019年3月13日 下午3:03:01
     * @param seckillInfo
     * @return
     */
    Result<SeckillInfo> updateSeckill(SeckillInfo seckillInfo);
    
    /**
     * 获取秒杀活动详细信息
     * 
     * @date 2019年5月21日 下午5:03:16
     * @param markId
     * @return
     */
    Result<SeckillInfo> getSeckillInfo(String markId);
}
