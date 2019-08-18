package com.szhengzhu.service;

import java.util.Date;

import com.szhengzhu.bean.activity.SeckillInfo;
import com.szhengzhu.bean.order.SeckillOrder;
import com.szhengzhu.bean.wechat.vo.SeckillModel;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

public interface SeckillOrderService {

    /**
     * 获取秒杀订单分页列表
     * 
     * @date 2019年3月14日 下午3:28:59
     * @param orderPage
     * @return
     */
    Result<PageGrid<SeckillOrder>> pageOrder(PageParam<SeckillOrder> orderPage);
    
    /**
     * 修改订单状态
     * 
     * @date 2019年4月1日 下午6:48:49
     * @param markId
     * @param orderStatus
     * @return
     */
    Result<?> updateStatus(String markId, String orderStatus);
    
    /**
     * 添加秒杀订单
     * 
     * @date 2019年5月22日 下午12:34:42
     * @param unifiedOrder
     * @param seckillInfo
     * @return
     */
    Result<SeckillOrder> addOrder(SeckillModel model, SeckillInfo seckillInfo);
    
    /**
     * 获取订单信息
     * 
     * @date 2019年7月24日 下午4:45:20
     * @param orderNo
     * @return
     */
    Result<SeckillOrder> getOrderByNo(String orderNo);
    
    /**
     * 获取订单详细信息
     * 
     * @date 2019年7月25日 下午4:18:55
     * @return
     */
    Result<SeckillOrder> getOrderById(String markId);
}
