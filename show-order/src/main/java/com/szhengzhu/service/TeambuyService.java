package com.szhengzhu.service;

import com.szhengzhu.bean.activity.TeambuyInfo;
import com.szhengzhu.bean.order.TeambuyGroup;
import com.szhengzhu.bean.order.TeambuyOrder;
import com.szhengzhu.bean.vo.ExportTemplateVo;
import com.szhengzhu.bean.wechat.vo.Judge;
import com.szhengzhu.bean.wechat.vo.OrderDetail;
import com.szhengzhu.bean.wechat.vo.TeambuyModel;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Jehon Zeng
 */
public interface TeambuyService {

    /**
     * 获取团购团单分页列表
     * 
     * @date 2019年3月15日 下午4:56:21
     * @param orderPage
     * @return
     */
    PageGrid<TeambuyGroup> pageGroup(PageParam<TeambuyGroup> groupPage);
    
    /**
     * 获取团单信息
     * 
     * @date 2019年10月22日 上午10:11:10
     * @param markId
     * @return
     */
    TeambuyGroup getInfo(String markId);

    /**
     * 获取团购子订单分页列表
     * 
     * @date 2019年3月25日 下午6:11:44
     * @param itemPage
     * @return
     */
    PageGrid<TeambuyOrder> pageOrder(PageParam<TeambuyOrder> orderPage);

    /**
     * 修改订单状态
     * 
     * @date 2019年4月1日 下午6:52:50
     * @param markId
     * @param orderStatus
     * @return
     */
    TeambuyOrder modifyStatus(String markId, String orderStatus);
    
    /**
     * 根据订单号修改订单状态
     * 
     * @date 2019年10月18日 下午6:10:43
     * @param orderNo
     * @param orderStatus
     * @param userId
     * @return
     */
    void modifyStatusNo(String orderNo, String orderStatus, String userId);
    
    /**
     * 添加团购订单信息
     * 
     * @date 2019年5月21日 下午5:42:48
     * @param model
     * @param teambuyInfo
     * @return
     */
    TeambuyOrder create(TeambuyModel model, TeambuyInfo teambuyInfo, BigDecimal deliverPrice, String storehouseId);
    
    /**
     * 获取订单信息
     * 
     * @date 2019年7月24日 下午4:47:33
     * @param orderNo
     * @return
     */
    TeambuyOrder getOrderByNo(String orderNo);
    
    /**
     * 获取订单详细信息
     * 
     * @date 2019年7月25日 下午4:18:12
     * @param markId
     * @return
     */
    TeambuyOrder getOrderById(String markId);

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
     * @date 2019年10月21日 下午4:22:38
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
