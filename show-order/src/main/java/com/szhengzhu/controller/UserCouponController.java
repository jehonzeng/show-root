package com.szhengzhu.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.order.UserCoupon;
import com.szhengzhu.bean.vo.CalcBase;
import com.szhengzhu.bean.vo.UserBase;
import com.szhengzhu.bean.wechat.vo.CouponBase;
import com.szhengzhu.client.ShowUserClient;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.service.UserCouponService;

@RestController
@RequestMapping("/usercoupon")
public class UserCouponController {

    @Resource
    private UserCouponService userCouponService;

    @Resource
    private ShowUserClient showUserClient;

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<UserCoupon>> pageCoupon(@RequestBody PageParam<UserCoupon> couponPage) {
        return userCouponService.pageCoupon(couponPage);
    }

    @RequestMapping(value = "/list/{userId}", method = RequestMethod.GET)
    public Result<List<CouponBase>> listByUser(@PathVariable("userId") String userId,
            @RequestParam("type") Integer type) {
        return userCouponService.listByUser(userId, type);
    }

    @RequestMapping(value = "/addByUser", method = RequestMethod.POST)
    public Result<?> sendCoupon(@RequestBody List<UserCoupon> base) {
        return userCouponService.addUserCoupon(base);
    }

    @RequestMapping(value = "/addByRole", method = RequestMethod.POST)
    public Result<?> sendCouponByRole(@RequestBody List<UserCoupon> base, @RequestParam("roleId") String roleId) {
        List<UserBase> userList = showUserClient.getUsersByRole(roleId);
        if (userList == null || userList.size() == 0)
            return new Result<>(StatusCode._5009);
        return userCouponService.addCouponByRole(base, userList);
    }

    @RequestMapping(value = "/{couponId}", method = RequestMethod.GET)
    public Result<UserCoupon> getInfo(@PathVariable("couponId") String couponId) {
        return userCouponService.getInfo(couponId);
    }

    @RequestMapping(value = "/calc/param", method = RequestMethod.GET)
    public Result<CalcBase> getCalcParam(@RequestParam(value = "couponId", required = false) String couponId,
            @RequestParam(value = "vouchers", required = false) List<String> vouchers,
            @RequestParam(value = "addressId", required = false) String addressId) {
        return userCouponService.getCalcParam(couponId, vouchers, addressId);
    }
}
