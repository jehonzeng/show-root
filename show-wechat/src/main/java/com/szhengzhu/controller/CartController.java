package com.szhengzhu.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.ShoppingCart;
import com.szhengzhu.bean.user.UserInfo;
import com.szhengzhu.bean.wechat.vo.OrderModel;
import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.client.ShowOrderClient;
import com.szhengzhu.client.ShowUserClient;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.util.StringUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "购物车：CartController" })
@RestController
@RequestMapping("/v1/cart")
public class CartController {

    @Resource
    private ShowGoodsClient showGoodsClient;

    @Resource
    private ShowUserClient showUserClient;
    
    @Resource
    private ShowOrderClient showOrderClient;

    @ApiOperation(value = "添加购物车", notes = "添加购物车")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result<?> add(HttpServletRequest request, @RequestBody ShoppingCart cart) {
        if (cart == null || StringUtils.isEmpty(cart.getProductId()) || cart.getProductType() == null
                || cart.getQuantity() == null)
            return new Result<>(StatusCode._4004);
        String token = request.getHeader("Show-Token");
        Result<UserInfo> userResult = showUserClient.getUserByToken(token);
        if (userResult.isSuccess()) {
            String userId = userResult.getData().getMarkId();
            cart.setUserId(userId);
            return showGoodsClient.addCart(cart);
        }
        return new Result<>(StatusCode._4012);
    }

    @ApiOperation(value = "修改购物车", notes = "修改购物车")
    @RequestMapping(value = "", method = RequestMethod.PATCH)
    public Result<?> modify(HttpServletRequest request, @RequestBody ShoppingCart cart) {
        if (cart == null || StringUtils.isEmpty(cart.getProductId()) || cart.getProductType() == null)
            return new Result<>(StatusCode._4004);
        String token = request.getHeader("Show-Token");
        Result<UserInfo> userResult = showUserClient.getUserByToken(token);
        if (userResult.isSuccess()) {
            String userId = userResult.getData().getMarkId();
            cart.setUserId(userId);
            return showGoodsClient.modifyCart(cart);
        }
        return new Result<>(StatusCode._4012);
    }

    @ApiOperation(value = "购物车列表", notes = "购物车列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<?> list(HttpServletRequest request) {
        String token = request.getHeader("Show-Token");
        Result<UserInfo> userResult = showUserClient.getUserByToken(token);
        if (userResult.isSuccess()) {
            String userId = userResult.getData().getMarkId();
            return showGoodsClient.listCart(userId);
        }
        return new Result<>(StatusCode._4012);
    }
    
    @ApiOperation(value = "购物车获取附属品、加价购等", notes = "购物车获取附属品、加价购等")
    @RequestMapping(value = "/addition", method = RequestMethod.GET)
    public Result<?> getCartAddition() {
        return showGoodsClient.getCartAddition();
    }
    
    @ApiOperation(value = "结算界面计算总价", notes = "返回总价和商品列表等")
    @RequestMapping(value = "/calc", method = RequestMethod.POST)
    public Result<?> calcTotal(@RequestBody OrderModel orderModel) {
        if (orderModel == null || orderModel.getItem().size() == 0)
            return new Result<>(StatusCode._4004);
        return showGoodsClient.calcTotal(orderModel);
    }
}
