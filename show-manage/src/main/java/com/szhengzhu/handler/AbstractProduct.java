package com.szhengzhu.handler;

import com.szhengzhu.bean.base.ImageInfo;
import com.szhengzhu.bean.vo.ProductInfo;
import com.szhengzhu.core.Result;

/**
 * @author Administrator
 */
public abstract class AbstractProduct {

    /**
     * 获取商品图片
     * 
     * @date 2019年9月24日 下午2:37:08
     * @param productId
     * @param specIds
     * @return
     */
    abstract public Result<ImageInfo> showProductImg(String productId, String specIds);

    /**
     * 添加商品虚拟库存
     * 
     * @date 2019年10月18日 上午11:45:35
     * @param productInfo
     */
    abstract public void addCurrentStock(ProductInfo productInfo);

    /**
     * 减商品虚拟库存
     * 
     * @date 2019年10月18日 上午11:45:45
     * @param productInfo
     */
    abstract public void subCurrentStock(ProductInfo productInfo);

    /**
     * 减商品真实库存
     * 
     * @date 2019年10月18日 上午11:45:54
     * @param productInfo
     */
    abstract public void subTotalStock(ProductInfo productInfo);

    /**
     * 加商品真实库存
     * 
     * @date 2019年10月18日 上午11:46:07
     * @param productInfo
     */
    abstract public void addTotalStock(ProductInfo productInfo);
    
    /**
     * 扫码中奖赠送商品
     * 
     * @param productId
     * @param openId
     */
    abstract public void scanWinner(String productId, String openId);
}
