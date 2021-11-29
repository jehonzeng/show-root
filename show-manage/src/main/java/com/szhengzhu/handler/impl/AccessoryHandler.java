package com.szhengzhu.handler.impl;


import com.szhengzhu.annotation.ProductType;
import com.szhengzhu.client.ShowBaseClient;
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
@ProductType(Contacts.TYPE_OF_ACCESSORY)
public class AccessoryHandler extends AbstractProduct {
    
    @Resource
    private ShowBaseClient showBaseClient;

    @Override
    public Result<ImageInfo> showProductImg(String productId, String specIds) {
        return showBaseClient.showAccessorylImage(productId);
    }

    @Override
    public void addCurrentStock(ProductInfo productInfo) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void subCurrentStock(ProductInfo productInfo) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void subTotalStock(ProductInfo productInfo) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addTotalStock(ProductInfo productInfo) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void scanWinner(String productId, String openId) {
        // TODO Auto-generated method stub
        
    }
}
