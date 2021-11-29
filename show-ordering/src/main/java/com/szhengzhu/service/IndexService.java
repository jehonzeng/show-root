package com.szhengzhu.service;

import com.szhengzhu.bean.ordering.GoodsSales;
import com.szhengzhu.bean.ordering.param.GoodSaleParam;
import com.szhengzhu.bean.ordering.param.GoodSaleRankParam;
import com.szhengzhu.bean.ordering.param.GoodTypeSaleParam;
import com.szhengzhu.bean.ordering.param.IndexParam;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;
import java.util.Map;

public interface IndexService {
    /**
     * 查询今日的营业额
     *
     * @param storeId
     * @return
     */
    Map<String, Object> todayTurnover(String storeId);

    /**
     * 查询近7天的营业额
     *
     * @param storeId
     * @return
     */
    List<Map<String, Object>> weekTurnover(String storeId);

    /**
     * 查询菜品销售排行
     *
     * @param pageParam
     * @return
     */
    PageGrid<GoodSaleRankParam> goodsSalesRank(PageParam<?> pageParam,String storeId);

    /**
     * 菜品销量排行
     *
     * @param pageParam
     * @return
     */
    PageGrid<GoodSaleParam> goodsSaleCompare(PageParam<?> pageParam,String storeId);

    /**
     * 菜品分类销售排行
     *
     * @param storeId
     * @return
     */
    List<GoodTypeSaleParam> goodsTypeSale(String storeId);

    /**
     * 实收
     *
     * @param storeId
     * @return
     */
    IndexParam netReceipts(String storeId);


    /**
     * 买单量(昨日买单量，昨日人数)
     *
     * @param storeId
     * @return
     */
    Map<String, Object> info(String storeId);

    /**
     * 菜品的销售
     *
     * @param goodsSales
     * @return
     */
    List<GoodSaleRankParam> goodsSales(GoodsSales goodsSales);

    /**
     * 本周上周实收
     * @param storeId
     * @return
     */
    Map<String,Object> weekNetReceipts(String storeId);
}
