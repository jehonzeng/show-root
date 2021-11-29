package com.szhengzhu.controller;

import com.szhengzhu.bean.goods.CookCertified;
import com.szhengzhu.bean.goods.CookFollow;
import com.szhengzhu.bean.order.UserAddress;
import com.szhengzhu.bean.user.UserToken;
import com.szhengzhu.bean.vo.UserInfoVo;
import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.client.ShowOrderClient;
import com.szhengzhu.client.ShowUserClient;
import com.szhengzhu.core.Result;
import com.szhengzhu.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jehon Zeng
 */
@Validated
@Slf4j
@Api(tags = {"用户专题：UserController"})
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
    @GetMapping(value = "/info")
    public Result<?> getUserInfo(HttpServletRequest request) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        String userId = userToken.getUserId();
        Map<String, Object> result = new HashMap<>(4);
        Result<UserInfoVo> myResult = showUserClient.getMyInfo(userId);
        Result<UserAddress> address = showOrderClient.getDefAddressByUser(userId);
        result.put("info", myResult.getData());
        result.put("default", address.isSuccess() ? address.getData() : null);
        return new Result<>(result);
    }

    @ApiOperation(value = "获取用户线上优惠券", notes = "获取用户线上优惠券")
    @GetMapping(value = "/coupon/list")
    public Result<?> coupon(HttpServletRequest request) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        return showOrderClient.listCouponByUser(userToken.getUserId(), 1);
    }

    @ApiOperation(value = "获取用户线下代金券", notes = "获取用户线下优惠券")
    @GetMapping(value = "/voucher/list")
    public Result<?> voucher(HttpServletRequest request) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        return showOrderClient.listCouponByUser(userToken.getUserId(), 0);
    }

    @ApiOperation(value = "获取用户商品代金券", notes = "获取用户商品代金券")
    @GetMapping(value = "/goods/voucher/list")
    public Result<?> goodsVoucher(HttpServletRequest request) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        return showOrderClient.listVoucherByUser(userToken.getUserId());
    }

    @ApiOperation(value = "获取用户地址列表", notes = "获取用户地址列表")
    @GetMapping(value = "/address/list")
    public Result<?> address(HttpServletRequest request) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        return showOrderClient.listAddressByUser(userToken.getUserId());
    }

    @ApiOperation(value = "添加用户配送地址信息", notes = "添加用户配送地址信息")
    @PostMapping(value = "/address")
    public Result<UserAddress> addAddress(HttpServletRequest request, @RequestBody @Validated UserAddress address) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        address.setUserId(userToken.getUserId());
        log.info(address.toString());
        return showOrderClient.addAddress(address);
    }

    @ApiOperation(value = "修改用户配送地址信息", notes = "修改用户配送地址信息")
    @PatchMapping(value = "/address")
    public Result modifyAddress(HttpServletRequest request, @RequestBody @Validated UserAddress address) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        address.setUserId(userToken.getUserId());
        return showOrderClient.modifyAddress(address);
    }

    @ApiOperation(value = "获取用户地址详细信息", notes = "获取用户地址详细信息")
    @GetMapping(value = "/address/{addressId}")
    public Result<UserAddress> getAddress(@PathVariable("addressId") @NotBlank String addressId) {
        return showOrderClient.getAddressInfo(addressId);
    }

    @ApiOperation(value = "用户关注或取关厨师", notes = "用户关注或取关厨师")
    @GetMapping(value = "/cooker/follow/{cooker}/{follow}")
    public Result followCooker(HttpServletRequest request, @PathVariable("cooker") @NotBlank String cooker,
                                  @PathVariable("follow") @NotNull Integer follow) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        CookFollow cookFollow = CookFollow.builder().userId(userToken.getUserId()).cookId(cooker).follow(follow).build();
        return showGoodsClient.cookFollowOr(cookFollow);
    }

    @ApiOperation(value = "厨师自荐添加信息", notes = "厨师自荐添加信息")
    @PostMapping(value = "/cooker")
    public Result<CookCertified> add(HttpServletRequest request, @RequestBody @Validated CookCertified cooker) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        cooker.setUserId(userToken.getUserId());
        return showGoodsClient.addCertified(cooker);
    }
}
