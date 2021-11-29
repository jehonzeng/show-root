package com.szhengzhu.service;

import com.szhengzhu.bean.ordering.DiscountInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

public interface DiscountService {

    /**
     * 添加店员可操作的商品折扣信息
     * @param base
     * @return
     */
    void addDiscount(DiscountInfo base);

    /**
     * 获取店员可操作的商品折扣
     *
     * @param base
     * @return
     */
    PageGrid<DiscountInfo> getPage(PageParam<DiscountInfo> base);

    /**
     * @param markId
     * @return
     */
    DiscountInfo getDiscountInfo(String markId);

    /**
     * 修改折扣信息
     *
     * @param base
     * @return
     */
    void modifyDiscount(DiscountInfo base);

    /**
     * 批量折扣修改状态
     *
     * @param discountIds
     * @param status
     * @return
     */
    void modifyStatus(String[] discountIds, Integer status);

    /**
     * 删除折扣信息（状态：-1）
     *
     * @param discountId
     * @return
     */
    void deleteDiscount(String discountId);

    /**
     * 点餐平台获取商品折扣方案
     *
     * @param employeeId
     * @param storeId
     * @return
     */
    List<Combobox> listCombobox(String employeeId, String storeId);

}
