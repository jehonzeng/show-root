package com.szhengzhu.service;

import com.szhengzhu.bean.activity.SeckillInfo;
import com.szhengzhu.bean.order.SeckillOrder;
import com.szhengzhu.bean.vo.ExportTemplateVo;
import com.szhengzhu.bean.wechat.vo.Judge;
import com.szhengzhu.bean.wechat.vo.OrderDetail;
import com.szhengzhu.bean.wechat.vo.SeckillModel;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Jehon Zeng
 */
public interface SeckillService {

    /**
     * 获取秒杀订单分页列表
     * 
     * @date 2019年3月14日 下午3:28:59
     * @param orderPage
     * @return
     */
    PageGrid<SeckillOrder> pageOrder(PageParam<SeckillOrder> orderPage);
    
    /**
     * 修改订单状态
     * 
     * @date 2019年4月1日 下午6:48:49
     * @param markId
     * @param orderStatus
     * @return
     */
    SeckillOrder modifyStatus(String markId, String orderStatus);
    
    /**
     * 根据订单号修改订单状态
     * 
     * @date 2019年10月18日 下午6:08:15
     * @param orderNo
     * @param orderStatus
     * @param userId
     * @return
     */
    void modifyStatusNo(String orderNo, String orderStatus, String userId);
    
    /**
     * 用户下单
     * 
     * @date 2019年5月22日 下午12:34:42
     * @param unifiedOrder
     * @param seckillInfo
     * @return
     */
    SeckillOrder create(SeckillModel model, SeckillInfo seckillInfo, BigDecimal deliverPrice, String storehouseId);
    
    /**
     * 获取订单信息
     * 
     * @date 2019年7月24日 下午4:45:20
     * @param orderNo
     * @return
     */
    SeckillOrder getOrderByNo(String orderNo);
    
    /**
     * 获取订单详细信息
     * 
     * @date 2019年7月25日 下午4:18:55
     * @return
     */
    SeckillOrder getOrderById(String markId);

    /**
     * 获取订单打印信息
     *
     * @param markId
     * @return
     * @date 2019年9月5日
     */
    ExportTemplateVo getPrintOrderInfo(String markId);
    
    /**
     * 获取订单详情
     * 
     * @date 2019年10月21日 下午4:22:12
     * @param orderNo
     * @param userId
     * @return
     */
    OrderDetail getOrderDetail(String orderNo, String userId);
    
    /**
     * 获取订单待评论商品
     * 
     * @date 2019年10月21日 下午5:14:37
     * @param orderNo
     * @param userId
     * @return
     */
    List<Judge> listItemJudge(String orderNo, String userId);
}
