package com.szhengzhu.service;

import java.util.List;
import java.util.Map;

import com.szhengzhu.bean.goods.ShoppingCart;
import com.szhengzhu.bean.order.UserAddress;
import com.szhengzhu.bean.order.UserCoupon;
import com.szhengzhu.bean.order.UserVoucher;
import com.szhengzhu.bean.wechat.vo.OrderModel;
import com.szhengzhu.core.Result;

public interface ShoppingCartService {

    /**
     * 添加用户购物车信息
     * 
     * @date 2019年6月20日 下午5:56:01
     * @param cart
     * @return
     */
    Result<?> addCart(ShoppingCart cart);
    
    /**
     * 修改用户购物车信息
     * 
     * @date 2019年6月20日 下午5:56:38
     * @return
     */
    Result<?> modifyCart(ShoppingCart cart);
    
    /**
     * 获取用户购物车列表
     * 
     * @date 2019年6月20日 下午5:57:32
     * @param userId
     * @return
     */
    Result<List<ShoppingCart>> listCart(String userId);
    
    /**
     * 购物车附加选项（附属品、加价购等）
     * 
     * @date 2019年7月1日 下午3:22:03
     * @return
     */
    Result<Map<String, Object>> getCartAddition();
    
    /**
     * 购物车进结算
     * 
     * @date 2019年7月3日 下午5:03:44
     * @param orderModel
     * @param voucherMap
     * @param coupon
     * @param address
     * @return
     */
    Result<?> calcTotal(OrderModel orderModel, Map<String, UserVoucher> voucherMap, UserCoupon coupon, UserAddress address);
}
