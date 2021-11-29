package com.szhengzhu.handler.impl;

import com.szhengzhu.annotation.ProductType;
import com.szhengzhu.client.ShowBaseClient;
import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.bean.base.ImageInfo;
import com.szhengzhu.bean.vo.ProductInfo;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.Result;
import com.szhengzhu.handler.AbstractProduct;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Component
@ProductType(Contacts.TYPE_OF_GOODS)
public class GoodsHandler extends AbstractProduct {

    @Resource
    private ShowBaseClient showBaseClient;

    @Resource
    private ShowGoodsClient showGoodsClient;

    @Override
    public Result<ImageInfo> showProductImg(String productId, String specIds) {
        return showBaseClient.showGoodsSpecImage(productId, specIds);
    }

    @Override
    public void addCurrentStock(ProductInfo productInfo) {
        showGoodsClient.addGoodsCurrentStock(productInfo);
    }

    @Override
    public void subCurrentStock(ProductInfo productInfo) {
        showGoodsClient.subGoodsCurrentStock(productInfo);
    }

    @Override
    public void subTotalStock(ProductInfo productInfo) {
        showGoodsClient.subGoodsTotalStock(productInfo);
    }

    @Override
    public void addTotalStock(ProductInfo productInfo) {
        showGoodsClient.addGoodsTotalStock(productInfo);
    }

    @Override
    public void scanWinner(String productId, String openId) {
        // TODO Auto-generated method stub
        
    }
}
