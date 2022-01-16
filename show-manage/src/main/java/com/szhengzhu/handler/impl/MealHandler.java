package com.szhengzhu.handler.impl;

import com.szhengzhu.annotation.ProductType;
import com.szhengzhu.feign.ShowBaseClient;
import com.szhengzhu.bean.base.ImageInfo;
import com.szhengzhu.bean.vo.ProductInfo;
import com.szhengzhu.feign.ShowGoodsClient;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.Result;
import com.szhengzhu.handler.AbstractProduct;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Component
@ProductType(Contacts.TYPE_OF_MEAL)
public class MealHandler extends AbstractProduct {
    
    @Resource
    private ShowBaseClient showBaseClient;
    
    @Resource
    private ShowGoodsClient ShowGoodsClient;

    @Override
    public Result<ImageInfo> showProductImg(String productId, String specIds) {
        return showBaseClient.showMealSmallImage(productId);
    }

    @Override
    public void addCurrentStock(ProductInfo productInfo) {
        ShowGoodsClient.addMealCurrentStock(productInfo);
    }

    @Override
    public void subCurrentStock(ProductInfo productInfo) {
        ShowGoodsClient.subMealCurrentStock(productInfo);
    }

    @Override
    public void subTotalStock(ProductInfo productInfo) {
        ShowGoodsClient.subMealTotalStock(productInfo);
    }

    @Override
    public void addTotalStock(ProductInfo productInfo) {
        ShowGoodsClient.addMealTotalStock(productInfo);
    }

    @Override
    public void scanWinner(String productId, String openId) {
        // TODO Auto-generated method stub
        
    }

}
