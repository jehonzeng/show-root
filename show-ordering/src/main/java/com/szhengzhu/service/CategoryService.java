package com.szhengzhu.service;

import com.szhengzhu.bean.ordering.Category;
import com.szhengzhu.bean.ordering.CategoryCommodity;
import com.szhengzhu.bean.ordering.CategorySpecs;
import com.szhengzhu.bean.ordering.vo.CategoryVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.xwechat.vo.CategoryModel;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

public interface CategoryService {

    /**
     * 获取分类分页列表
     *
     * @param pageParam
     * @return
     */
    PageGrid<CategoryVo> page(PageParam<Category> pageParam);

    /**
     * 添加分类
     *
     * @param category
     * @return
     */
    void add(Category category);

    /**
     * 修改分类
     *
     * @param category
     * @return
     */
    void modify(Category category);

    /**
     * 批量修改分类状态
     * @param cateIds
     * @param status
     * @return
     */
    void modifyStatus(String[] cateIds, int status);

    /**
     * 获取分类键值对列表
     *
     * @param storeId
     * @return
     */
    List<Combobox> listCombobox(String storeId);

    /**
     * 修改分类规格关联关系
     *
     * @param categorySpecs
     * @return
     */
    void modifyCateSpecs(CategorySpecs categorySpecs);

    /**
     * 删除分类规格关联关系
     *
     * @param specsIds
     * @param cateId
     * @return
     */
    void optCateSpecs(String[] specsIds, String cateId);

    /**
     * 修改分类商品信息
     *
     * @param categoryCommodity
     * @return
     */
    void modifyCateCommodity(CategoryCommodity categoryCommodity);

    /**
     * 删除分类商品
     *
     * @param cateId
     * @param commodityIds
     * @return
     */
    void optCateCommodity(String cateId, String[] commodityIds);

    /**
     * 点餐平台获取分类及分类商品
     *
     * @param storeId
     * @return
     */
    List<CategoryModel> listResCate(String storeId);

    /**
     * 小程序获取分类及分类商品
     *
     * @param storeId
     * @return
     */
    List<CategoryModel> listLjsCate(String storeId);
}
