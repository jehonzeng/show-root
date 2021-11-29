package com.szhengzhu.handler;

import com.szhengzhu.core.Result;

import java.awt.image.BufferedImage;
import java.io.InputStream;

/**
 * @author Administrator
 */
public abstract class AbstractOrder {

    /**
     * 打印(普通，团购，秒杀)订单图片
     * 
     * @date 2019年9月24日 下午3:30:29
     * @param orderId
     * @return
     */
    abstract public Result<?> getExportOrderInfo(String orderId);
    
    /**
     * 创建图片
     * 
     * @date 2019年9月24日 下午3:49:20
     * @param data
     * @param in
     * @return
     */
    abstract public BufferedImage createExportImage(Object data,InputStream in);
    
    /**
     * 获取订单商品列表，加商品虚拟库存
     * 
     * @date 2019年10月18日 上午11:35:53
     * @param orderNo
     */
    abstract public void addCurrentStock(String orderNo);
    
    /**
     * 获取订单商品列表，减商品虚拟库存
     * 
     * @date 2019年10月18日 上午11:36:53
     * @param orderNo
     */
    abstract public void subCurrentStock(String orderNo);
    
    /**
     * 获取订单商品列表，减商品真实库存
     * 
     * @date 2019年10月18日 上午11:38:05
     * @param orderNo
     */
    abstract public void subTotalStock(String orderNo);
    
    /**
     * 获取订单商品列表， 加商品真实库存
     * 
     * @date 2019年10月18日 上午11:38:47
     * @param orderNo
     */
    abstract public void addTotalStock(String orderNo);
}
