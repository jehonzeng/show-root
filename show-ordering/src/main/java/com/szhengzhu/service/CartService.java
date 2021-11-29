package com.szhengzhu.service;

import com.szhengzhu.bean.xwechat.param.CartParam;

import java.util.Map;

public interface CartService {

    /**
     * 添加购物车商品
     *
     * @param cartParam
     * @return
     */
    String add(CartParam cartParam);

    /**
     * 修改购物车商品
     *
     * @param cartParam
     * @return
     */
    void modify(CartParam cartParam);

    /**
     * 清空购物车
     *
     * @param tableId
     * @return
     */
    void clearCart(String tableId);

    /**
     * 获取购物车列表
     *
     * @param tableId
     * @return
     */
    Map<String, Object> list(String tableId);
}
