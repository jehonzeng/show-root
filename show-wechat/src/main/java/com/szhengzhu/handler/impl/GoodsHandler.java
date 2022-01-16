package com.szhengzhu.handler.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.szhengzhu.annotation.ProductType;
import com.szhengzhu.feign.ShowGoodsClient;
import com.szhengzhu.bean.goods.GoodsJudge;
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
@ProductType(Contacts.TYPE_OF_GOODS)
public class GoodsHandler extends AbstractProduct {

    @Resource
    private ShowGoodsClient showGoodsClient;

    @Override
    public Result<GoodsDetail> getProductDetail(String productId, String userId) {
        return showGoodsClient.getGoodsDetail(productId, userId);
    }

    @Override
    public Result<StockBase> getProductStock(StockParam stockParam) {
        return showGoodsClient.getGoodsStcokInfo(stockParam.getGoodsId(), stockParam.getSpecIds(),
                stockParam.getAddressId());
    }

    @Override
    public Result<List<JudgeBase>> listGoodsJudge(String productId, String userId) {
        return showGoodsClient.listGoodsJudge(productId, userId);
    }

    @Override
    public void addProductJudge(Judge judge, String orderId, String userId, String nickName) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        GoodsJudge goodsJudge = GoodsJudge.builder()
                .markId(snowflake.nextIdStr())
                .goodsId(judge.getProductId()).orderId(orderId)
                .userId(userId).serverStatus(false)
                .description(judge.getDescription()).commentator(nickName)
                .addTime(DateUtil.date()).star(judge.getStar()).build();
        showGoodsClient.addGoodsJudge(goodsJudge);
    }

}
