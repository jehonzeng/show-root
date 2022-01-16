package com.szhengzhu.service;

import com.szhengzhu.bean.ordering.Commodity;
import com.szhengzhu.bean.ordering.CommodityItem;
import com.szhengzhu.bean.ordering.CommoditySpecs;
import com.szhengzhu.bean.ordering.param.CommodityParam;
import com.szhengzhu.bean.ordering.vo.CommodityPageVo;
import com.szhengzhu.bean.ordering.vo.CommodityVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;
import java.util.Map;

public interface CommodityService {

    /**
     * 获取商品分页列表
     *
     * @param pageParam
     * @return
     */
    PageGrid<CommodityPageVo> page(PageParam<CommodityParam> pageParam);

    /**
     * 获取商品信息
     *
     * @param commodityId
     * @return
     */
    Commodity getInfo(String commodityId);

    /**
     * 获取商品详细信息
     *
     * @param commodityId
     * @return
     */
    CommodityVo getInfoVo(String commodityId);

    /**
     * 添加商品
     *
     * @param commodity
     * @return
     */
    String add(Commodity commodity);

    /**
     * 修改商品
     *
     * @param commodity
     * @return
     */
    void modify(Commodity commodity);

    /**
     * 修改商品数量
     *
     * @param commodityId
     * @param employeeId
     * @param quantity
     * @return
     */
    void modifyQuantity(String commodityId, String employeeId, Integer quantity);

    /**
     * 批量修改商品状态
     *
     * @param commodityIds
     * @param status
     * @return
     */
    void modifyStatus(String[] commodityIds, int status);

    /**
     * 获取检索商品列表
     *
     * @param name
     * @return
     */
    List<Map<String, String>> listCommodity(String name);

    /**
     * 添加商品规格
     *
     * @param commoditySpecs
     * @return
     */
    void optSpecs(CommoditySpecs commoditySpecs);

    /**
     * 添加商品规格值
     *
     * @param commodityItem
     * @return
     */
    void optSpecsItem(CommodityItem commodityItem);

    /**
     * 删除商品规格值
     *
     * @param commodityId
     * @param specsId
     * @param itemId
     * @return
     */
    void deleteSpecsItem(String commodityId, String specsId, String itemId);

    /**
     * 操作商品分类
     *
     * @param commodityId
     * @param cateIds
     * @return
     */
    void optCommodityCate(String commodityId, String[] cateIds);

    /**
     * 操作商品标签
     *
     * @param commodityId
     * @param tagIds
     * @return
     */
    void optCommodityTag(String commodityId, String[] tagIds);

    List<Commodity> queryComboCommodity(String markId);
}
