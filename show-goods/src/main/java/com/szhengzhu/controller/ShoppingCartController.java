package com.szhengzhu.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.ShoppingCart;
import com.szhengzhu.bean.order.HolidayInfo;
import com.szhengzhu.bean.order.UserAddress;
import com.szhengzhu.bean.order.UserCoupon;
import com.szhengzhu.bean.order.UserVoucher;
import com.szhengzhu.bean.vo.CalcBase;
import com.szhengzhu.bean.wechat.vo.OrderModel;
import com.szhengzhu.client.ShowOrderClient;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.service.ShoppingCartService;
import com.szhengzhu.util.StringUtils;

@RestController
@RequestMapping("/carts")
public class ShoppingCartController {

    @Resource
    private ShoppingCartService shoppingCartService;

    @Resource
    private ShowOrderClient showOrderClient;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<?> add(@RequestBody ShoppingCart cart) {
        if (cart == null || StringUtils.isEmpty(cart.getProductId()) || cart.getProductType() == null
                || cart.getQuantity() == null)
            return new Result<>(StatusCode._4004);
        return shoppingCartService.addCart(cart);
    }

    @RequestMapping(value = "/modify", method = RequestMethod.PATCH)
    public Result<?> modify(@RequestBody ShoppingCart cart) {
        if (cart == null || StringUtils.isEmpty(cart.getProductId()) || cart.getProductType() == null)
            return new Result<>(StatusCode._4004);
        return shoppingCartService.modifyCart(cart);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<List<ShoppingCart>> list(@RequestParam("userId") String userId) {
        if (StringUtils.isEmpty(userId))
            return new Result<>(StatusCode._4004);
        return shoppingCartService.listCart(userId);
    }

    @RequestMapping(value = "/addition", method = RequestMethod.GET)
    public Result<Map<String, Object>> getCartAddition() {
        return shoppingCartService.getCartAddition();
    }

    @RequestMapping(value = "/calc", method = RequestMethod.POST)
    public Result<?> calc(@RequestBody OrderModel orderModel) {
        if (orderModel == null || orderModel.getItem().size() == 0)
            return new Result<>(StatusCode._4004);
        Map<String, UserVoucher> voucherMap = new HashMap<>();
        UserAddress address = null;
        UserCoupon coupon = null;
        List<String> vouchers = new LinkedList<>();
        if (orderModel.getVoucher() != null && orderModel.getVoucher().size() > 0)
            orderModel.getVoucher().forEach((voucher) -> vouchers.add(voucher.getVoucherId()));
        if (!StringUtils.isEmpty(orderModel.getCouponId()) || vouchers.size() > 0
                || StringUtils.isEmpty(orderModel.getAddressId())) {
            Result<CalcBase> calcResult = showOrderClient.getCalcParam(orderModel.getCouponId(), vouchers,
                    orderModel.getAddressId());
            if (calcResult.isSuccess()) {
                voucherMap = calcResult.getData().getVoucherMap();
                coupon = calcResult.getData().getCoupon();
                address = calcResult.getData().getAddress();
                if (address != null && address.getSendToday()) {
                    if (orderModel.getDelvieryDate() == null)
                        return new Result<>(StatusCode._4004);
                    Result<HolidayInfo> holidayResult = showOrderClient.getHoliday(orderModel.getDelvieryDate());
                    if (!holidayResult.isSuccess())
                        return new Result<>(StatusCode._5013);
                }  
            }
        }
        return shoppingCartService.calcTotal(orderModel, voucherMap, coupon, address);
    }
}
