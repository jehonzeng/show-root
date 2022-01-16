package com.szhengzhu.controller;

import com.szhengzhu.feign.ShowBaseClient;
import com.szhengzhu.feign.ShowUserClient;
import com.szhengzhu.bean.base.CouponTemplate;
import com.szhengzhu.bean.order.UserCoupon;
import com.szhengzhu.bean.vo.UserBase;
import com.szhengzhu.bean.wechat.vo.CouponBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.service.UserCouponService;
import com.szhengzhu.util.CouponUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用户优惠券操作
 *
 * @author Jehon Zeng
 */
@Validated
@RestController
@RequestMapping("/usercoupon")
public class UserCouponController {

    @Resource
    private UserCouponService userCouponService;

    @Resource
    private ShowUserClient showUserClient;

    @Resource
    private ShowBaseClient showBaseClient;

    @PostMapping(value = "/page")
    public PageGrid<UserCoupon> pageCoupon(@RequestBody PageParam<UserCoupon> couponPage) {
        return userCouponService.pageCoupon(couponPage);
    }

    @GetMapping(value = "/list/{userId}")
    public List<CouponBase> listByUser(@PathVariable("userId") @NotBlank String userId,
                                       @RequestParam("type") @NotNull Integer type) {
        return userCouponService.listByUser(userId, type);
    }

    @GetMapping(value = "/addByUser")
    public void sendCoupon(@RequestParam("userId") @NotBlank String userId,
                           @RequestParam("templateId") @NotBlank String templateId) {
        Result<CouponTemplate> result = showBaseClient.getCouponTemplate(templateId);
        ShowAssert.checkNull(result.getData(), StatusCode._5009);
        List<UserCoupon> list = CouponUtils.createCoupon(result.getData(), userId);
        ShowAssert.checkTrue(list.isEmpty(), StatusCode._5009);
        userCouponService.addUserCoupon(list);
    }

    @GetMapping(value = "/addByRole")
    public void sendCouponByRole(@RequestParam("roleId") @NotBlank String roleId,
                                 @RequestParam("templateId") @NotBlank String templateId) {
        Result<CouponTemplate> result = showBaseClient.getCouponTemplate(templateId);
        ShowAssert.checkNull(result.getData(), StatusCode._5009);
        List<UserCoupon> list = CouponUtils.createCoupon(result.getData(), "");
        List<UserBase> userList = showUserClient.getUsersByRole(roleId);
        ShowAssert.checkTrue(userList.isEmpty(), StatusCode._5009);
        userCouponService.addCouponByRole(list, userList);
    }

    @GetMapping(value = "/{couponId}")
    public UserCoupon getInfo(@PathVariable("couponId") String couponId) {
        return userCouponService.getInfo(couponId);
    }

    @PostMapping(value = "/packs/coupon")
    public void receivedPacksCoupon(@RequestBody List<UserCoupon> data) {
        userCouponService.addUserCoupon(data);
    }
}
