package com.szhengzhu.service;

import java.util.Date;

import com.szhengzhu.bean.activity.TeambuyInfo;
import com.szhengzhu.bean.order.TeambuyOrder;
import com.szhengzhu.bean.order.TeambuyGroup;
import com.szhengzhu.bean.wechat.vo.TeambuyModel;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

public interface TeambuyOrderService {

    /**
     * 获取团购团单分页列表
     * 
     * @date 2019年3月15日 下午4:56:21
     * @param orderPage
     * @return
     */
    Result<PageGrid<TeambuyGroup>> pageGroup(PageParam<TeambuyGroup> groupPage);

    /**
     * 获取团购子订单分页列表
     * 
     * @date 2019年3月25日 下午6:11:44
     * @param itemPage
     * @return
     */
    Result<PageGrid<TeambuyOrder>> pageOrder(PageParam<TeambuyOrder> orderPage);

    /**
     * 修改订单状态
     * 
     * @date 2019年4月1日 下午6:52:50
     * @param markId
     * @param orderStatus
     * @return
     */
    Result<?> updateStatus(String markId, String orderStatus);
    
    /**
     * 添加团购订单信息
     * 
     * @date 2019年5月21日 下午5:42:48
     * @param model
     * @param teambuyInfo
     * @return
     */
    Result<TeambuyOrder> addOrder(TeambuyModel model, TeambuyInfo teambuyInfo);
    
    /**
     * 获取订单信息
     * 
     * @date 2019年7月24日 下午4:47:33
     * @param orderNo
     * @return
     */
    Result<TeambuyOrder> getOrderByNo(String orderNo);
    
    /**
     * 获取订单详细信息
     * 
     * @date 2019年7月25日 下午4:18:12
     * @param markId
     * @return
     */
    Result<TeambuyOrder> getOrderById(String markId);
}
