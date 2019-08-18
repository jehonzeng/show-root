package com.szhengzhu.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.CookCertified;
import com.szhengzhu.bean.goods.CookFollow;
import com.szhengzhu.bean.order.UserAddress;
import com.szhengzhu.bean.user.UserInfo;
import com.szhengzhu.bean.user.UserToken;
import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.client.ShowOrderClient;
import com.szhengzhu.client.ShowUserClient;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.util.StringUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "用户模块：UserController" })
@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Resource
    private ShowUserClient showUserClient;

    @Resource
    private ShowOrderClient showOrderClient;

    @Resource
    private ShowGoodsClient showGoodsClient;

    @ApiOperation(value = "商城我的模块获取用户信息", notes = "商城我的模块获取用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Result<?> getUserInfo(HttpServletRequest request) {
        String token = request.getHeader("Show-Token");
        Result<UserInfo> userResult = showUserClient.getUserByToken(token);
        String userId = null;
        if (userResult.isSuccess())
            userId = userResult.getData().getMarkId();
        else
            return new Result<>(StatusCode._4012);
        Map<String, Object> result = new HashMap<>();
        Result<Map<String, Object>> myInfo = showUserClient.getMyInfo(userId);
        Result<UserAddress> address = showOrderClient.getDefAddressByUser(userId);
        result.put("info", myInfo.isSuccess() ? myInfo.getData() : null);
        result.put("default", address.isSuccess() ? address.getData() : null);
        return new Result<>(result);
    }

    @ApiOperation(value = "获取用户线上优惠券", notes = "获取用户线上优惠券")
    @RequestMapping(value = "/coupon/list", method = RequestMethod.GET)
    public Result<?> coupon(HttpServletRequest request) {
        String token = request.getHeader("Show-Token");
        Result<UserInfo> userResult = showUserClient.getUserByToken(token);
        if (userResult.isSuccess())
            return showOrderClient.listCouponByUser(userResult.getData().getMarkId(), 1);
        return new Result<>(StatusCode._4012);
    }

    @ApiOperation(value = "获取用户线下代金券", notes = "获取用户线下优惠券")
    @RequestMapping(value = "/voucher/list", method = RequestMethod.GET)
    public Result<?> voucher(HttpServletRequest request) {
        String token = request.getHeader("Show-Token");
        Result<UserInfo> userResult = showUserClient.getUserByToken(token);
        if (userResult.isSuccess()) {
            String userId = userResult.getData().getMarkId();
            return showOrderClient.listCouponByUser(userId, 0);
        }
        return new Result<>(StatusCode._4012);
    }

    @ApiOperation(value = "获取用户商品代金券", notes = "获取用户商品代金券")
    @RequestMapping(value = "/goods/voucher/list", method = RequestMethod.GET)
    public Result<?> goodsVoucher(HttpServletRequest request) {
        String token = request.getHeader("Show-Token");
        Result<UserInfo> userResult = showUserClient.getUserByToken(token);
        if (userResult.isSuccess()) {
            String userId = userResult.getData().getMarkId();
            return showOrderClient.listVoucherByUser(userId);
        }
        return new Result<>(StatusCode._4012);
    }

    @ApiOperation(value = "获取用户地址列表", notes = "获取用户地址列表")
    @RequestMapping(value = "/address/list", method = RequestMethod.GET)
    public Result<?> address(HttpServletRequest request) {
        String token = request.getHeader("Show-Token");
        Result<UserInfo> userResult = showUserClient.getUserByToken(token);
        if (userResult.isSuccess()) {
            String userId = userResult.getData().getMarkId();
            return showOrderClient.listAddressByUser(userId);
        }
        return new Result<>(StatusCode._4012);
    }

    @ApiOperation(value = "添加用户配送地址信息", notes = "添加用户配送地址信息")
    @RequestMapping(value = "/address", method = RequestMethod.POST)
    public Result<?> addAddress(HttpServletRequest request, @RequestBody UserAddress address) {
        if (address == null || StringUtils.isEmpty(address.getUserName()) || StringUtils.isEmpty(address.getPhone())
                || StringUtils.isEmpty(address.getArea()) || StringUtils.isEmpty(address.getCity())
                || StringUtils.isEmpty(address.getUserAddress()))
            return new Result<>(StatusCode._4004);
        String token = request.getHeader("Show-Token");
        Result<UserInfo> userResult = showUserClient.getUserByToken(token);
        if (userResult.isSuccess()) {
            String userId = userResult.getData().getMarkId();
            address.setUserId(userId);
            return showOrderClient.addAddress(address);
        }
        return new Result<>(StatusCode._4012);
    }

    @ApiOperation(value = "修改用户配送地址信息", notes = "修改用户配送地址信息")
    @RequestMapping(value = "/address", method = RequestMethod.PATCH)
    public Result<?> modifyAddress(HttpServletRequest request, @RequestBody UserAddress address) {
        if (address == null || StringUtils.isEmpty(address.getMarkId()))
            return new Result<>(StatusCode._4004);
        String token = request.getHeader("Show-Token");
        Result<UserInfo> userResult = showUserClient.getUserByToken(token);
        if (userResult.isSuccess()) {
            String userId = userResult.getData().getMarkId();
            address.setUserId(userId);
            return showOrderClient.modifyAddress(address);
        }
        return new Result<>(StatusCode._4012);
    }

    @ApiOperation(value = "获取用户地址详细信息", notes = "获取用户地址详细信息")
    @RequestMapping(value = "/address/{addressId}", method = RequestMethod.GET)
    public Result<UserAddress> getAddress(@PathVariable("addressId") String addressId) {
        if (StringUtils.isEmpty(addressId))
            return new Result<>(StatusCode._4004);
        return showOrderClient.getAddressInfo(addressId);
    }

    @ApiOperation(value = "用户关注或取关厨师", notes = "用户关注或取关厨师")
    @RequestMapping(value = "/cooker/follow/{cooker}/{follow}", method = RequestMethod.GET)
    public Result<?> followCooker(HttpServletRequest request, @PathVariable("cooker") String cooker,
            @PathVariable("follow") Integer follow) {
        if (StringUtils.isEmpty(cooker) || follow == null)
            return new Result<>(StatusCode._4004);
        String token = request.getHeader("Show-Token");
        Result<UserInfo> userResult = showUserClient.getUserByToken(token);
        if (userResult.isSuccess()) {
            CookFollow cookFollow = new CookFollow();
            String userId = userResult.getData().getMarkId();
            cookFollow.setUserId(userId);
            cookFollow.setCookId(cooker);
            cookFollow.setFollow(follow);
            return showGoodsClient.cookFollowOr(cookFollow);
        }
        return new Result<>(StatusCode._4012);
    }

    @ApiOperation(value = "厨师自荐添加信息", notes = "厨师自荐添加信息")
    @RequestMapping(value = "/cooker", method = RequestMethod.POST)
    public Result<?> add(HttpServletRequest request, @RequestBody CookCertified cooker) {
        if (StringUtils.isEmpty(cooker.getShortName()) || StringUtils.isEmpty(cooker.getPhone())
                || StringUtils.isEmpty(cooker.getCookStyle()))
            return new Result<>(StatusCode._4004);
        String token = request.getHeader("Show-Token");
        String userId = null;
        if (token != null) {
            Result<UserToken> tokenResult = showUserClient.getUserToken(token);
            if (tokenResult.isSuccess())
                userId = tokenResult.getData().getUserId();
        }
        if (StringUtils.isEmpty(userId))
            return new Result<>(StatusCode._4013);
        cooker.setUserId(userId);
        return showGoodsClient.addCooker(cooker);
    }
}
