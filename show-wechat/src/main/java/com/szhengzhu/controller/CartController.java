package com.szhengzhu.controller;

import com.szhengzhu.feign.ShowGoodsClient;
import com.szhengzhu.feign.ShowUserClient;
import com.szhengzhu.bean.goods.ShoppingCart;
import com.szhengzhu.bean.user.UserToken;
import com.szhengzhu.bean.wechat.vo.CalcData;
import com.szhengzhu.bean.wechat.vo.CartModel;
import com.szhengzhu.bean.wechat.vo.OrderModel;
import com.szhengzhu.core.Result;
import com.szhengzhu.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Jehon Zeng
 */
@Api(tags = {"购物车专题：CartController"})
@RestController
@RequestMapping("/v1/cart")
public class CartController {

    @Resource
    private ShowGoodsClient showGoodsClient;

    @Resource
    private ShowUserClient showUserClient;

    @ApiOperation(value = "添加购物车", notes = "添加购物车")
    @PostMapping(value = "")
    public Result<ShoppingCart> add(HttpServletRequest request, @RequestBody @Validated ShoppingCart cart) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        cart.setUserId(userToken.getUserId());
        return showGoodsClient.addCart(cart);
    }

    @ApiOperation(value = "修改购物车", notes = "修改购物车")
    @PatchMapping(value = "")
    public Result<ShoppingCart> modify(HttpServletRequest request, @RequestBody @Validated ShoppingCart cart) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        cart.setUserId(userToken.getUserId());
        return showGoodsClient.modifyCart(cart);
    }

    @ApiOperation(value = "刷新用户购物车", notes = "刷新用户购物车")
    @PatchMapping(value = "/refresh")
    public Result refresh(HttpServletRequest request, @RequestBody List<ShoppingCart> cartList) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        CartModel cartModel = new CartModel();
        cartModel.setUserId(userToken.getUserId());
        cartModel.setCartList(cartList);
        return showGoodsClient.refreshCart(cartModel);
    }

    @ApiOperation(value = "购物车列表", notes = "购物车列表")
    @GetMapping(value = "/list")
    public Result list(HttpServletRequest request) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        return showGoodsClient.listCart(userToken.getUserId());
    }

    @ApiOperation(value = "购物车获取附属品、加价购等", notes = "购物车获取附属品、加价购等")
    @GetMapping(value = "/addition")
    public Result<Map<String, Object>> getCartAddition() {
        return showGoodsClient.getCartAddition();
    }

    @ApiOperation(value = "结算界面计算总价", notes = "返回总价和商品列表等")
    @PostMapping(value = "/calc")
    public Result<CalcData> calcTotal(@RequestBody @Validated OrderModel orderModel) {
        return showGoodsClient.calcTotal(orderModel);
    }
}
