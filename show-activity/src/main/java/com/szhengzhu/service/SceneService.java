package com.szhengzhu.service;

import com.szhengzhu.bean.activity.SceneGoods;
import com.szhengzhu.bean.activity.SceneInfo;
import com.szhengzhu.bean.activity.SceneItem;
import com.szhengzhu.bean.activity.SceneOrder;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;
import java.util.Map;

public interface SceneService {

    /**
     * 获取现场活动分页列表
     * 
     * @param page
     * @return
     */
    PageGrid<SceneInfo> pageScene(PageParam<SceneInfo> page);
    
    /**
     * 获取现场活动详细信息
     * 
     * @param sceneId
     * @return
     */
    SceneInfo getSceneInfo(String sceneId);
    
    /**
     * 添加现场活动
     * 
     * @param sceneInfo
     * @return
     */
    void addScene(SceneInfo sceneInfo);
    
    /**
     * 修改现场活动信息
     * 
     * @param sceneInfo
     * @return
     */
    void modifyScene(SceneInfo sceneInfo);
    
    /**
     * 获取现场活动商品分页列表
     * 
     * @param page
     * @return
     */
    PageGrid<SceneGoods> pageGoods(PageParam<SceneGoods> page);
    
    /**
     * 获取商品详细信息
     * 
     * @param goodsId
     * @return
     */
    SceneGoods getGoodsInfo(String goodsId);
    
    /**
     * 添加商品信息
     * 
     * @param goods
     * @return
     */
    void addGoods(SceneGoods goods);
    
    /**
     * 修改商品信息
     * 
     * @param goods
     * @return
     */
    void modifyGoods(SceneGoods goods);
    
    /**
     * 获取订单分页列表
     * 
     * @param page
     * @return
     */
    PageGrid<SceneOrder> pageOrder(PageParam<SceneOrder> page);
    
    /**
     * 获取商品列表
     * 
     * @return
     */
    List<SceneGoods> listForeGoods();
    
    /**
     * 现场下单支付
     * 
     * @param goodsIdList
     * @param userId
     * @return
     */
    Map<String, Object> createOrder(List<String> goodsIdList, String userId);
    
    /**
     * 获取未领取的商品
     * 
     * @param userId
     * @return
     */
    List<SceneItem> listUnReceiveGoods(String userId);
    
    /**
     * 获取已领取商品
     * 
     * @param userId
     * @return
     */
    List<SceneItem> listReceiveGoods(String userId);
    
    /**
     * 获取订单信息
     * 
     * @param orderNo
     * @return
     */
    SceneOrder getOrderInfo(String orderNo);
    
   /**
    * 修改订单状态
    * 
    * @param orderNo
    * @param orderStatus
    */
    void modifyOrderStatus(String orderNo, String orderStatus);
    
    /**
     * 用户领取商品
     * 
     * @param idsList
     * @param userId
     * @return
     */
    List<String> receiveGoods(List<String> idsList, String userId);
}
