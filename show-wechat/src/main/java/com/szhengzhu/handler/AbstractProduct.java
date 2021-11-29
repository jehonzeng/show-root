package com.szhengzhu.handler;

import com.szhengzhu.bean.wechat.vo.*;
import com.szhengzhu.core.Result;

import java.util.List;

/**
 * @author Jehon Zeng
 */
public abstract class AbstractProduct {

    /**
     * 获取商品详情
     * 
     * @param productId
     * @param userId
     * @return
     */
    abstract public Result<GoodsDetail> getProductDetail(String productId, String userId);
    
    /**
     * 获取商品库存
     * 
     * @param stockParam
     * @return
     */
    abstract public Result<StockBase> getProductStock(StockParam stockParam);
    
    /**
     * 获取商品评论列表
     * 
     * @param productId
     * @param userId
     * @return
     */
    abstract public Result<List<JudgeBase>> listGoodsJudge(String productId, String userId);
    
    /**
     * 添加商品评论
     * 
     * @param judge
     * @param orderId
     * @param userId
     * @param nickName
     */
    abstract public void addProductJudge(Judge judge, String orderId, String userId, String nickName);
}
