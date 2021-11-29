package com.szhengzhu.service;

import com.szhengzhu.bean.excel.OrderSendModel;
import com.szhengzhu.bean.excel.ProductModel;
import com.szhengzhu.bean.vo.TodayProductVo;
import com.szhengzhu.bean.wechat.vo.OrderBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;
import java.util.Map;

/**
 * @author Jehon Zeng
 */
public interface OrderAllService {

    /**
     * 获取用户所有订单列表
     * 
     * @param orderPage
     * @return
     */
    PageGrid<OrderBase> listAll(PageParam<String> orderPage);

    /**
     * 获取用户未支付的订单列表
     * @param orderPage
     * @return
     */
    PageGrid<OrderBase> listUnpaid(PageParam<String> orderPage);

    /**
     * 获取用户未成团的订单列表
     * 
     * @param orderPage
     * @return
     */
    PageGrid<OrderBase> listUngroup(PageParam<String> orderPage);

    /**
     * 获取用户未配送的订单列表
     * @param orderPage
     * @return
     */
    PageGrid<OrderBase> listUndelivery(PageParam<String> orderPage);

    /**
     * 获取用户未收货的订单列表
     * @param orderPage
     * @return
     */
    PageGrid<OrderBase> listUnReceive(PageParam<String> orderPage);

    /**
     * 获取用户未评价的订单列表
     * 
     * @param orderPage
     * @return
     */
    PageGrid<OrderBase> listUnjudge(PageParam<String> orderPage);

    /**
     * 获取（普通、秒杀、团购）（已支付或备货）中包含单品和套餐的商品列表
     *
     * @return
     * @date 2019年9月16日
     */
    List<TodayProductVo> getTodayList();
    
    /**
     * 获取各状态的订单数量
     * @param userId
     * @return
     */
    List<Map<String, Object>> getStatusCount(String userId);
    
    /**
     * 获取当天发货订单分页列表
     * 
     * @param page
     * @return
     */
    PageGrid<OrderSendModel> pageSendInfo(PageParam<String> page);
    
    /**
     * 获取当天发货订单列表
     * @return
     */
    List<OrderSendModel> listSendInfo();
    
    /**
     * 获取当天备货商品分页列表
     * 
     * @return
     */
    PageGrid<ProductModel> pageTodayProductQuantity(PageParam<?> pageParam);
    
    /**
     * 获取当天备货数量列表
     * 
     * @return
     */
    List<ProductModel> listTodayProductQuantity();

}