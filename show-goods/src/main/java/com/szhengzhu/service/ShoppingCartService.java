package com.szhengzhu.service;

import com.szhengzhu.bean.goods.ShoppingCart;
import com.szhengzhu.bean.wechat.vo.CalcData;
import com.szhengzhu.bean.wechat.vo.OrderModel;

import java.util.List;
import java.util.Map;

public interface ShoppingCartService {

    /**
     * 添加用户购物车信息
     * 
     * @date 2019年6月20日 下午5:56:01
     * @param cart
     * @return
     */
    ShoppingCart addCart(ShoppingCart cart);
    
    /**
     * 修改用户购物车信息
     * 
     * @date 2019年6月20日 下午5:56:38
     * @return
     */
    void modifyCart(ShoppingCart cart);
    
    /**
     * 刷新用户购物车
     * 
     * @date 2019年7月29日 下午2:50:01
     * @param userId
     * @param cartList
     * @return
     */
    void refresh(String userId, List<ShoppingCart> cartList);
    
    /**
     * 获取用户购物车列表
     * 
     * @date 2019年6月20日 下午5:57:32
     * @param userId
     * @return
     */
    List<ShoppingCart> listCart(String userId);
    
    /**
     * 购物车附加选项（附属品、加价购等）
     * 
     * @date 2019年7月1日 下午3:22:03
     * @return
     */
    Map<String, Object> getCartAddition();
    
    /**
     * 购物车进结算
     * 
     * @date 2019年7月3日 下午5:03:44
     * @param orderModel
     * @return
     */
    CalcData calcTotal(OrderModel orderModel);
}
