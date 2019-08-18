package com.szhengzhu.service;

import com.szhengzhu.bean.order.OrderDelivery;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

public interface OrderDeliveryService {

    /**
     * 获取订单配送列表
     * 
     * @date 2019年3月13日 下午5:44:54
     * @param deliveryPage
     * @return
     */
    Result<PageGrid<OrderDelivery>> pageDelivery(PageParam<OrderDelivery> deliveryPage);
    
    /**
     * 获取订单配送详情
     * 
     * @date 2019年3月22日 下午3:17:55
     * @param orderId
     * @return
     */
    Result<OrderDelivery> getDeliveryByOrderId(String orderId);
    
    /**
     * 修改配送信息
     * 
     * @date 2019年5月15日 下午5:19:54
     * @param orderDelivery
     * @return
     */
    Result<?> modifyDelivery(OrderDelivery orderDelivery);
}
