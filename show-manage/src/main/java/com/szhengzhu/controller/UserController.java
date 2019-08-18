package com.szhengzhu.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.order.UserAddress;
import com.szhengzhu.bean.order.UserCoupon;
import com.szhengzhu.bean.base.CouponTemplate;
import com.szhengzhu.bean.goods.CookCertified;
import com.szhengzhu.bean.user.UserInfo;
import com.szhengzhu.bean.user.UserIntegral;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.IntegralVo;
import com.szhengzhu.bean.vo.UserVo;
import com.szhengzhu.client.ShowBaseClient;
import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.client.ShowOrderClient;
import com.szhengzhu.client.ShowUserClient;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.util.CouponUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "用户管理：UserController" })
@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Resource
    private ShowUserClient showUserClient;

    @Resource
    private ShowOrderClient showOrderClient;

    @Resource
    private ShowGoodsClient showGoodsClient;

    @Resource
    private ShowBaseClient showBaseClient;

    @ApiOperation(value = "获取用户分页列表", notes = "获取用户分页列表，当roleIds不为空时，查询属于该角色集的用户")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<UserVo>> pageUser(@RequestBody PageParam<UserVo> userPage) {
        return showUserClient.pageUser(userPage);
    }

    @ApiOperation(value = "获取用户分页列表", notes = "获取用户分页列表，当roleIds不为空时，查询出不属于该角色集的用户")
    @RequestMapping(value = "/page/notin", method = RequestMethod.POST)
    public Result<PageGrid<UserVo>> pageUserNotIn(@RequestBody PageParam<UserVo> userPage) {
        return showUserClient.pageUserNotIn(userPage);
    }

    @ApiOperation(value = "获取用户详细信息", notes = "获取用户详细信息")
    @RequestMapping(value = "/{markId}", method = RequestMethod.GET)
    public Result<UserInfo> getUserById(@PathVariable("markId") String markId) {
        return showUserClient.getUserById(markId);
    }

    @ApiOperation(value = "认证厨师信息", notes = "可修改厨师认证信息")
    @RequestMapping(value = "/cook", method = RequestMethod.PATCH)
    public Result<CookCertified> modifyCertified(@RequestBody CookCertified cookCertified) {
        return showGoodsClient.modifyCertified(cookCertified);
    }

    @ApiOperation(value = "录入待认证厨师信息", notes = "录入待认证厨师信息")
    @RequestMapping(value = "/cook", method = RequestMethod.POST)
    public Result<CookCertified> saveCertified(@RequestBody CookCertified cookCertified) {
        return showGoodsClient.addCertified(cookCertified);
    }

    @ApiOperation(value = "获取厨师认证分页列表", notes = "获取厨师认证分页列表")
    @RequestMapping(value = "/cook/page", method = RequestMethod.POST)
    public Result<PageGrid<CookCertified>> pageCook(
            @RequestBody PageParam<CookCertified> cookPage) {
        return showGoodsClient.pageCook(cookPage);
    }

    @ApiOperation(value = "获取用户优惠券分页列表", notes = "获取用户优惠券分页列表")
    @RequestMapping(value = "/coupon/page", method = RequestMethod.POST)
    public Result<PageGrid<UserCoupon>> pageCoupon(@RequestBody PageParam<UserCoupon> couponPage) {
        return showOrderClient.pageCoupon(couponPage);
    }

    @ApiOperation(value = "获取用户积分结算分页列表", notes = "获取用户积分结算分页列表")
    @RequestMapping(value = "/integraltotal/page", method = RequestMethod.POST)
    public Result<PageGrid<IntegralVo>> pageIntegralTotal(
            @RequestBody PageParam<Map<String, String>> integralPage) {
        return showUserClient.pageIntegralTotal(integralPage);
    }

    @ApiOperation(value = "获取用户积分使用情况分页列表", notes = "获取用户积分使用情况分页列表")
    @RequestMapping(value = "/integral/page", method = RequestMethod.POST)
    public Result<PageGrid<UserIntegral>> pageIntegral(PageParam<UserIntegral> integralPage) {
        return showUserClient.pageIntegral(integralPage);
    }

    @ApiOperation(value = "获取用户地址列表", notes = "获取用户地址列表")
    @RequestMapping(value = "/address/page", method = RequestMethod.POST)
    public Result<PageGrid<UserAddress>> pageAddress(
            @RequestBody PageParam<UserAddress> addressPage) {
        return showOrderClient.pageAddress(addressPage);
    }

    @ApiOperation(value = "获取用户下拉列表", notes = "获取用户下拉列表")
    @RequestMapping(value = "/combobox", method = RequestMethod.GET)
    public Result<List<Combobox>> getUserList() {
        return showUserClient.getUserList();
    }

    @ApiOperation(value = "管理员指定用户发送某种优惠券", notes = "管理员指定用户发送某种优惠券")
    @RequestMapping(value = "/coupon/send", method = RequestMethod.GET)
    public Result<?> sendCoupon(@RequestParam("userId") String userId,
            @RequestParam("templateId") String templateId) {
        CouponTemplate template = showBaseClient.getCouponTmplate(templateId);
        if (template == null)
            return new Result<>(StatusCode._5009);
        List<UserCoupon> list = CouponUtils.createCoupon(template, userId);
        return showOrderClient.sendCoupon(list);
    }
}
