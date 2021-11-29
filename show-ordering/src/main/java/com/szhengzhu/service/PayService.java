package com.szhengzhu.service;

import com.szhengzhu.bean.ordering.Pay;
import com.szhengzhu.bean.ordering.PayType;
import com.szhengzhu.bean.ordering.vo.PayBaseVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

public interface PayService {

    /**
     * 获取支付方式分页列表
     *
     * @param pageParam
     * @return
     */
    PageGrid<Pay> page(PageParam<Pay> pageParam);

    /**
     * 添加支付方式
     *
     * @param pay
     * @return
     */
    String add(Pay pay);

    /**
     * 修改支付方式
     *
     * @param pay
     * @return
     */
    void modify(Pay pay);

    /**
     * 删除支付方式
     *
     * @param payId
     * @return
     */
    void delete(String payId);

    /**
     * 点餐平台获取支付方式
     *
     * @param storeId
     * @return
     */
    List<PayBaseVo> resListPay(String storeId);

    /**
     * 获取支付方式类型分页列表
     *
     * @param pageParam
     * @return
     */
    PageGrid<PayType> pageType(PageParam<PayType> pageParam);

    /**
     * 添加支付方式类型
     *
     * @param payType
     * @return
     */
    String addType(PayType payType);

    /**
     * 修改支付方式类型
     *
     * @param payType
     * @return
     */
    void modifyType(PayType payType);

    /**
     * 删除支付方式类型
     *
     * @param typeId
     * @return
     */
    void deleteType(String typeId);

    /**
     * 获取支付方式键值对下拉列表
     *
     * @param storeId
     * @return
     */
    List<Combobox> getTypeCombobox(String storeId);
}
