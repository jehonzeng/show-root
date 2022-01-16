package com.szhengzhu.handler.impl;

import com.szhengzhu.annotation.ProductType;
import com.szhengzhu.feign.ShowBaseClient;
import com.szhengzhu.feign.ShowGoodsClient;
import com.szhengzhu.feign.ShowOrderClient;
import com.szhengzhu.feign.ShowUserClient;
import com.szhengzhu.bean.base.ImageInfo;
import com.szhengzhu.bean.user.UserInfo;
import com.szhengzhu.bean.vo.ProductInfo;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.Result;
import com.szhengzhu.handler.AbstractProduct;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Jehon Zeng
 */
@Component
@ProductType(Contacts.TYPE_OF_VOUCHER)
public class VoucherHandler extends AbstractProduct {
    
    @Resource
    private ShowBaseClient showBaseClient;
    
    @Resource
    private ShowGoodsClient showGoodsClient;
    
    @Resource
    private ShowUserClient showUserClient;
    
    @Resource
    private ShowOrderClient showOrderClient;

    @Override
    public Result<ImageInfo> showProductImg(String productId, String specIds) {
        return showBaseClient.showVoucherSpecImage(productId);
    }

    @Override
    public void addCurrentStock(ProductInfo productInfo) {
        showGoodsClient.addVoucherStock(productInfo.getProductId(), productInfo.getQuantity());
    }

    @Override
    public void subCurrentStock(ProductInfo productInfo) {
        showGoodsClient.subVoucherStock(productInfo.getProductId(), productInfo.getQuantity());
    }

    @Override
    public void subTotalStock(ProductInfo productInfo) {
        showGoodsClient.subVoucherStock(productInfo.getProductId(), productInfo.getQuantity());
    }

    @Override
    public void addTotalStock(ProductInfo productInfo) {
        showGoodsClient.addVoucherStock(productInfo.getProductId(), productInfo.getQuantity());
    }

    @Override
    public void scanWinner(String productId, String openId) {
        Result<UserInfo> userResult = showUserClient.getUserByOpenId(openId);
        UserInfo userInfo = userResult.getData();
        showOrderClient.sendGoodsVoucher(userInfo.getMarkId(), productId);
    }
}
