package com.szhengzhu.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.GoodsJudge;
import com.szhengzhu.bean.goods.MealJudge;
import com.szhengzhu.bean.order.OrderInfo;
import com.szhengzhu.bean.user.UserInfo;
import com.szhengzhu.bean.user.UserIntegral;
import com.szhengzhu.bean.vo.SpecChooseBox;
import com.szhengzhu.bean.wechat.vo.Judge;
import com.szhengzhu.bean.wechat.vo.JudgeBase;
import com.szhengzhu.bean.wechat.vo.Label;
import com.szhengzhu.bean.wechat.vo.StockBase;
import com.szhengzhu.bean.wechat.vo.StockParam;
import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.client.ShowOrderClient;
import com.szhengzhu.client.ShowUserClient;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.StringUtils;
import com.szhengzhu.util.TimeUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "商品操作：GoodController" })
@RestController
@RequestMapping("/goods")
public class GoodController {

    @Resource
    private ShowGoodsClient showGoodsClient;

    @Resource
    private ShowUserClient showUserClient;
    
    @Resource
    private ShowOrderClient showOrderClient;

    @ApiOperation(value = "获取标签分类商品列表", notes = "获取标签分类商品列表")
    @RequestMapping(value = "/label/list", method = RequestMethod.GET)
    public Result<List<Label>> listLabelGoods() {
        return showGoodsClient.listLabelGoods();
    }

    @ApiOperation(value = "获取商品详情", notes = "获取商品详情")
    @RequestMapping(value = "/{productId}", method = RequestMethod.GET)
    public Result<?> getGoodsDetail(HttpServletRequest request, @PathVariable("productId") String productId,
            @RequestParam("productType") Integer productType) {
        if (StringUtils.isEmpty(productId) || productType == null)
            return new Result<>(StatusCode._4004);
        String token = request.getHeader("Show-Token");
        String userId = null;
        if (!StringUtils.isEmpty(token)) {
            Result<UserInfo> userResult = showUserClient.getUserByToken(token);
            if (userResult.isSuccess())
                userId = userResult.getData().getMarkId();
        }
        if (productType.equals(0)) // goods
            return showGoodsClient.getGoodsDetail(productId, userId);
        else if (productType.equals(1)) // voucher
            return showGoodsClient.getVoucherDetail(productId, userId);
        else if (productType.equals(2)) // meal
            return showGoodsClient.getMealDetail(productId, userId);
        return new Result<>();
    }

    @ApiOperation(value = "根据goodsId获取规格属性选项列表", notes = "根据goodsId获取规格属性选项列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<List<SpecChooseBox>> listSpecification(@RequestParam("goodsId") String goodsId) {
        if (StringUtils.isEmpty(goodsId))
            return new Result<>(StatusCode._4004);
        return showGoodsClient.listSpecification(goodsId);
    }

    @ApiOperation(value = "商品详情页获取规格商品是否配送及当前库存", notes = "商品详情页获取规格商品是否配送及当前库存")
    @RequestMapping(value = "/spec/stock", method = RequestMethod.POST)
    public Result<StockBase> getSpecStock(@RequestBody StockParam stockParam) {
        if (stockParam == null ||StringUtils.isEmpty(stockParam.getGoodsId()) || stockParam.getProductType() == null)
            return new Result<>(StatusCode._4004);
        int productType = stockParam.getProductType().intValue();
        if (productType== 0)
            return showGoodsClient.getGoodsStcokInfo(stockParam.getGoodsId(), stockParam.getSpecIds(), stockParam.getAddressId());
        else if (productType == 1)
            return showGoodsClient.getVoucherStock(stockParam.getGoodsId());
        else if (productType == 2)
            return showGoodsClient.getMealStock(stockParam.getGoodsId(), stockParam.getAddressId());
        return new Result<>(StatusCode._4004);
    }
    
    @ApiOperation(value = "商城获取商品评论列表", notes = "商城获取商品评论列表")
    @RequestMapping(value = "/judge/list/{productId}", method = RequestMethod.GET)
    public Result<List<JudgeBase>> listGoodJudge(HttpServletRequest request, @PathVariable("productId") String productId,
            @RequestParam("productType") Integer productType) {
        if (StringUtils.isEmpty(productId) || productType == null)
            return new Result<>(StatusCode._4004);
        String token = request.getHeader("Show-Token");
        String userId = null;
        if (!StringUtils.isEmpty(token)) {
            Result<UserInfo> userResult = showUserClient.getUserByToken(token);
            if (userResult.isSuccess())
                userId = userResult.getData().getMarkId();
        }
        if (productType.equals(0)) // goods
            return showGoodsClient.listGoodsJudge(productId, userId);
        else if (productType.equals(1)) // voucher
            return showGoodsClient.listVoucherJudge(productId, userId);
        else if (productType.equals(2)) // meal
            return showGoodsClient.listMealJudge(productId, userId);
        return new Result<>();
    }
    
    @ApiOperation(value = "订单商品评价", notes = "订单商品评价")
    @RequestMapping(value = "/judge", method = RequestMethod.POST)
    public Result<?> addJudge(HttpServletRequest request, @RequestBody List<Judge> judges) {
        if (judges.size() == 0)
            return new Result<>(StatusCode._4004);
        String token = request.getHeader("Show-Token");
        UserInfo userInfo = null;
        if (!StringUtils.isEmpty(token)) {
            Result<UserInfo> userResult = showUserClient.getUserByToken(token);
            if (userResult.isSuccess())
                userInfo = userResult.getData();
        }
        if (userInfo == null)
            return new Result<>(StatusCode._4013);
        String orderId = null;
        IdGenerator generator = IdGenerator.getInstance();
        for (int index = 0, size = judges.size(); index < size; index++) {
            Judge judge = judges.get(index);
            if (index == 0)
                orderId = judge.getOrderId();
            if (judge.getProductType().equals(2)) {
                MealJudge mealJudge = new MealJudge();
                mealJudge.setMarkId(generator.nexId());
                mealJudge.setMealId(judge.getProductId());
                mealJudge.setOrderId(judge.getOrderId());
                mealJudge.setUserId(userInfo.getMarkId());
                mealJudge.setServerStatus(false);
                mealJudge.setDescription(judge.getDescription());
                mealJudge.setCommentator(userInfo.getNickName());
                mealJudge.setAddTime(TimeUtils.today());
                mealJudge.setStar(judge.getStar());
                showGoodsClient.addMealJudge(mealJudge);
            } else {
                GoodsJudge goodsJudge = new GoodsJudge();
                goodsJudge.setMarkId(generator.nexId());
                goodsJudge.setGoodsId(judge.getProductId());
                goodsJudge.setOrderId(judge.getOrderId());
                goodsJudge.setUserId(userInfo.getMarkId());
                goodsJudge.setServerStatus(false);
                goodsJudge.setDescription(judge.getDescription());
                goodsJudge.setCommentator(userInfo.getNickName());
                goodsJudge.setAddTime(TimeUtils.today());
                goodsJudge.setStar(judge.getStar());
                showGoodsClient.addGoodsJudge(goodsJudge);
            }
        }
        modifyOrder(orderId);
        return new Result<>();
    }
    
    /**
     * 修改订单信息及添加用户积分
     * 
     * @date 2019年7月26日 上午10:48:36
     * @param orderId
     */
    private void modifyOrder(String orderId) {
        Result<?> orderResult = showOrderClient.modifyOrderStatus(orderId, "OT06");// 订单修改为已评价状态
        if (orderResult.isSuccess()) {
            OrderInfo orderInfo = (OrderInfo) orderResult.getData();
            UserIntegral integral = new UserIntegral();
            integral.setUserId(orderInfo.getUserId());
            integral.setIntegralLimit(orderInfo.getPayAmount().intValue());
            integral.setRemark("订单评价：" + orderInfo.getOrderNo());
            showUserClient.addIntegral(integral);
        }
    }
}
