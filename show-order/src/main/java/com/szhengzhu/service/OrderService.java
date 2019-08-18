package com.szhengzhu.service;

import java.util.List;

import com.szhengzhu.bean.order.OrderInfo;
import com.szhengzhu.bean.order.OrderItem;
import com.szhengzhu.bean.vo.CalcData;
import com.szhengzhu.bean.vo.StockVo;
import com.szhengzhu.bean.wechat.vo.Judge;
import com.szhengzhu.bean.wechat.vo.OrderBase;
import com.szhengzhu.bean.wechat.vo.OrderDetail;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

public interface OrderService {

    /**
     * 获取订单分页列表
     * 
     * @date 2019年3月11日 下午6:06:48
     * @param orderPage
     * @return
     */
    Result<PageGrid<OrderInfo>> pageOrder(PageParam<OrderInfo> orderPage);
    
    /**
     * 获取订单详情及订单其他信息
     * 
     * @date 2019年4月2日 下午5:40:20
     * @param markId
     * @return
     */
    Result<OrderInfo> getOrderInfo(String markId);
    
    /**
     * 获取订单详情信息
     * 
     * @date 2019年5月10日 下午6:02:10
     * @param markId
     * @return
     */
    Result<OrderInfo> getOrderByNo(String markId);
    
    /**
     * 后台系统添加订单
     * 
     * @date 2019年4月2日 下午5:46:22
     * @param order
     * @return
     */
    Result<?> addBackendOrder(OrderInfo order, List<StockVo> stocks);
    
    /**
     * 后台系统修改订单
     * 
     * @date 2019年4月4日 下午5:27:17
     * @param order
     * @param stocks
     * @return
     */
    Result<?> modifyBackendOrder(OrderInfo order, List<StockVo> stocks);
    
    /**
     * 获取订单详情列表
     * 
     * @date 2019年3月22日 下午3:26:11
     * @param orderId
     * @return
     */
    Result<List<OrderItem>> listItemByOrderId(String orderId);
    
    /**
     * 获取销售子单分页列表
     * 
     * @date 2019年5月16日 上午11:56:30
     * @param itemPage
     * @return
     */
    Result<PageGrid<OrderItem>> pageItem(PageParam<OrderItem> itemPage);
    
    /**
     *  修改订单状态
     *  
     * @date 2019年4月1日 下午6:43:45
     * @param markId
     * @param orderStatus
     * @return
     */
    Result<?> updateStatus(String markId, String orderStatus);
    
    /**
     * 获取用户所有订单列表
     * 
     * @date 2019年6月11日 下午2:25:16
     * @param userId
     * @return
     */
    Result<PageGrid<OrderBase>> listAll(PageParam<String> orderPage);
    
    /**
     * 获取用户未支付订单列表
     * 
     * @date 2019年6月11日 下午2:24:22
     * @param userId
     * @return
     */
    Result<PageGrid<OrderBase>> listUnpaid(PageParam<String> orderPage);
    
    /**
     * 获取用户未成团的订单列表
     * 
     * @date 2019年6月11日 下午2:26:04
     * @param userId
     * @return
     */
    Result<PageGrid<OrderBase>> listUngroup(PageParam<String> orderPage);
    
    /**
     * 获取用户未配送的订单列表
     * 
     * @date 2019年6月11日 下午2:26:47
     * @param userId
     * @return
     */
    Result<PageGrid<OrderBase>> listUndelivery(PageParam<String> orderPage);
    
    /**
     * 获取用户未收货的订单列表
     * 
     * @date 2019年6月11日 下午4:25:45
     * @param userId
     * @return
     */
    Result<PageGrid<OrderBase>> listUnReceive(PageParam<String> orderPage);
    
    /**
     * 获取用户未评价的订单列表
     * 
     * @date 2019年6月11日 下午2:29:03
     * @param userId
     * @return
     */
    Result<PageGrid<OrderBase>> listUnjudge(PageParam<String> orderPage);
    
    /**
     * 获取订单详情
     * 
     * @date 2019年6月28日 下午4:49:57
     * @param orderNo
     * @return
     */
    Result<OrderDetail> getOrderDetail(String orderNo);
    
    /**
     * 获取订单待评论商品
     * 
     * @date 2019年7月2日 下午12:00:10
     * @param orderId
     * @return
     */
    Result<List<Judge>> listItemJudge(String orderId);
    
    /**
     * 经过结算后的数据生成订单
     * 
     * @date 2019年7月4日 下午4:07:37
     * @param calcData
     * @return
     */
    Result<?> createOrder(CalcData calcData) throws CloneNotSupportedException;
    
    /**
     * 用户支付订单失败回调修改订单信息
     * 
     * @date 2019年7月24日 下午3:40:10
     * @param orderNo
     */
    void orderErrorBack(String orderNo, String userId);
    
    /**
     * 用户支付订单成功回调修改订单状态
     * @date 2019年7月26日 上午9:31:33
     * @param orderNo
     * @return
     */
    List<OrderItem> orderInfoBack(String orderNo);
}
