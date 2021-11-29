package com.szhengzhu.handler.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.szhengzhu.annotation.ProductType;
import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.bean.goods.MealJudge;
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
@ProductType(Contacts.TYPE_OF_MEAL)
public class MealHandler extends AbstractProduct {

    @Resource
    private ShowGoodsClient showGoodsClient;

    @Override
    public Result<GoodsDetail> getProductDetail(String productId, String userId) {
        return showGoodsClient.getMealDetail(productId, userId);
    }

    @Override
    public Result<StockBase> getProductStock(StockParam stockParam) {
        return showGoodsClient.getMealStock(stockParam.getGoodsId(), stockParam.getAddressId());
    }

    @Override
    public Result<List<JudgeBase>> listGoodsJudge(String productId, String userId) {
        return showGoodsClient.listMealJudge(productId, userId);
    }

    @Override
    public void addProductJudge(Judge judge, String orderId, String userId, String nickName) {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        MealJudge mealJudge = MealJudge.builder()
                .markId(snowflake.nextIdStr())
                .mealId(judge.getProductId()).orderId(orderId)
                .userId(userId).serverStatus(false)
                .description(judge.getDescription()).commentator(nickName)
                .addTime(DateUtil.date()).star(judge.getStar()).build();
        showGoodsClient.addMealJudge(mealJudge);
    }
}
