package com.szhengzhu.service;

import com.szhengzhu.bean.order.BackHistory;
import com.szhengzhu.bean.order.OrderRecord;
import com.szhengzhu.bean.order.RefundBack;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

/**
 * @author Jehon Zeng
 */
public interface BackService {

    /**
     * 用户下单回调信息分页列表
     * 
     * @date 2019年10月15日 下午4:24:31
     * @param pageParam
     * @return
     */
    PageGrid<BackHistory> pageOrderBack(PageParam<BackHistory> pageParam);
    
    /**
     * 添加订单支付回调信息
     * 
     * @date 2019年10月17日 下午4:43:48
     * @param backHistory
     * @return
     */
    void addOrderBack(BackHistory backHistory);
    
    /**
     * 订单退款信息分页列表
     * 
     * @date 2019年10月15日 下午4:26:35
     * @param pageParam
     * @return
     */
    PageGrid<RefundBack> pageRefundBack(PageParam<RefundBack> pageParam);
    
    /**
     * 添加退款记录
     * 
     * @date 2019年10月17日 下午4:40:59
     * @param refundBack
     * @return
     */
    void addRefundBack(RefundBack refundBack);
    
    /**
     * 获取订单退款详细记录
     * 
     * @date 2019年10月17日 下午4:28:54
     * @param markId
     * @return
     */
    RefundBack getRefundBackInfo(String markId);
    
    /**
     * 添加订单支付失败信息
     * 
     * @date 2019年10月18日 下午4:13:01
     * @param orderNo
     * @param userId
     * @return
     */
    void addErrorBack(String orderNo, String userId);
    
    /**
     * 添加订单操作记录
     * 
     * @param orderRecord
     */
    void addOrderRecord(OrderRecord orderRecord);
}
