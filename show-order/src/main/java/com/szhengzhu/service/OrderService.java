package com.szhengzhu.service;

import com.szhengzhu.bean.order.OrderDelivery;
import com.szhengzhu.bean.order.OrderInfo;
import com.szhengzhu.bean.order.OrderItem;
import com.szhengzhu.bean.rpt.IndexDisplay;
import com.szhengzhu.bean.vo.OrderBatch;
import com.szhengzhu.bean.vo.OrderExportVo;
import com.szhengzhu.bean.wechat.vo.CalcData;
import com.szhengzhu.bean.wechat.vo.Judge;
import com.szhengzhu.bean.wechat.vo.OrderDetail;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author Jehon Zeng
 */
public interface OrderService {

    /**
     * 获取订单分页列表
     *
     * @param orderPage
     * @return
     * @date 2019年3月11日 下午6:06:48
     */
    PageGrid<OrderInfo> pageOrder(PageParam<OrderInfo> orderPage);

    /**
     * 获取内部人员推销订单分页列表
     *
     * @param orderPage
     * @return
     */
    PageGrid<OrderInfo> pageShareOrder(PageParam<OrderInfo> orderPage);

    /**
     * 获取订单详情及订单其他信息
     *
     * @param markId
     * @return
     * @date 2019年4月2日 下午5:40:20
     */
    OrderInfo getOrderDeliveryItem(String markId);

    /**
     * 获取订单详情
     *
     * @param orderId
     * @return
     * @date 2019年7月31日 下午4:38:12
     */
    OrderInfo getOrderById(String orderId);

    /**
     * 获取订单详情信息
     *
     * @param markId
     * @return
     * @date 2019年5月10日 下午6:02:10
     */
    OrderInfo getOrderByNo(String markId);

    /**
     * 后台系统添加订单
     *
     * @return
     * @date 2019年4月2日 下午5:46:22
     */
    String addBackendOrder(CalcData calcData, OrderDelivery orderDelivery, int orderType, BigDecimal deliveryAmount) throws Exception;

    /**
     * 后台系统修改订单
     *
     * @param order
     * @return
     * @date 2019年4月4日 下午5:27:17
     */
    void modifyBackendOrder(OrderInfo order);

    /**
     * 获取订单详情列表
     *
     * @param orderId
     * @return
     * @date 2019年3月22日 下午3:26:11
     */
    List<OrderItem> listItemByOrderId(String orderId);

    /**
     * 获取销售子单分页列表
     *
     * @param itemPage
     * @return
     * @date 2019年5月16日 上午11:56:30
     */
    PageGrid<OrderItem> pageItem(PageParam<OrderItem> itemPage);

    /**
     * 修改订单状态
     *
     * @param orderId
     * @param orderStatus
     * @return
     * @date 2019年4月1日 下午6:43:45
     */
    OrderInfo modifyStatus(String orderId, String orderStatus);

    /**
     * 根据订单号修改订单状态
     *
     * @param orderNo
     * @param orderStatus
     * @param userId
     * @return
     * @date 2019年10月18日 下午5:54:12
     */
    void modifyStatusByNo(String orderNo, String orderStatus, String userId);

    /**
     * 获取订单详情
     *
     * @param orderNo
     * @param userId
     * @return
     * @date 2019年6月28日 下午4:49:57
     */
    OrderDetail getOrderDetail(String orderNo, String userId);

    /**
     * 获取订单待评论商品
     *
     * @param orderNo
     * @param userId
     * @return
     * @date 2019年7月2日 下午12:00:10
     */
    List<Judge> listItemJudge(String orderNo, String userId);

    /**
     * 经过结算后的数据生成订单
     *
     * @param calcData
     * @return
     * @date 2019年7月4日 下午4:07:37
     */
    Map<String, Object> create(CalcData calcData) throws Exception;

    /**
     * 获取状态订单列表
     *
     * @param orderInfo
     * @return
     * @date 2019年7月30日 下午4:42:07
     */
    List<OrderInfo> listStatusOrder(String orderStatus);

    /**
     * 根据markId获取导单信息
     *
     * @param markId
     * @return
     * @date 2019年9月2日
     */
    OrderExportVo getExportOrdersById(String markId);

    /**
     * 获取首页订单统计数据展示
     *
     * @return
     * @date 2019年10月18日
     */
    List<IndexDisplay> getIndexStatusCount();

    /**
     * 批量备货
     *
     * @param base
     * @return
     */
    List<OrderInfo> batchUpdateStatus(OrderBatch base);
}
