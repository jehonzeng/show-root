package com.szhengzhu.handler.impl;

import com.szhengzhu.annotation.ProductType;
import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.bean.wechat.vo.*;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.Result;
import com.szhengzhu.handler.AbstractProduct;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jehon Zeng
 */
@Component
@ProductType(Contacts.TYPE_OF_VOUCHER)
public class VoucherHandler extends AbstractProduct {

    @Resource
    private ShowGoodsClient showGoodsClient;

    @Override
    public Result<GoodsDetail> getProductDetail(String productId, String userId) {
        return showGoodsClient.getVoucherDetail(productId, userId);
    }

    @Override
    public Result<StockBase> getProductStock(StockParam stockParam) {
        return showGoodsClient.getVoucherStock(stockParam.getGoodsId());
    }

    @Override
    public Result<List<JudgeBase>> listGoodsJudge(String productId, String userId) {
        return showGoodsClient.listVoucherJudge(productId, userId);
    }

    @Override
    public void addProductJudge(Judge judge, String judgeId, String userId, String nickName) {
        // no action
    }
}
