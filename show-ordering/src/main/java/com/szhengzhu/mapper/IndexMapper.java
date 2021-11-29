package com.szhengzhu.mapper;

import com.szhengzhu.bean.ordering.GoodsSales;
import com.szhengzhu.bean.ordering.param.GoodSaleParam;
import com.szhengzhu.bean.ordering.param.GoodSaleRankParam;
import com.szhengzhu.bean.ordering.param.GoodTypeSaleParam;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 首页
 *
 * @author Administrator
 */
public interface IndexMapper {

    /**
     * 今天的销量情况
     *
     * @param storeId
     * @return
     */
    Map<String, Object> todayTurnover(@Param("storeId") String storeId);

    /**
     * 近七天的销售额
     *
     * @param storeId
     * @return
     */
    List<Map<String, Object>> weekTurnover(@Param("storeId") String storeId);

    /**
     * 菜品的销售排行
     *
     * @param storeId
     * @return
     */
    List<GoodSaleRankParam> goodsSalesRank(@Param("storeId") String storeId);

    /**
     * 昨天人数，昨天买单量
     *
     * @param storeId
     * @return
     */
    Map<String, Object> yesterdaySituation(@Param("storeId") String storeId);

    /**
     * 今日实收
     *
     * @param storeId
     * @return
     */
    BigDecimal todayNetReceipts(@Param("storeId") String storeId);

    /**
     * 昨日实收
     *
     * @param storeId
     * @return
     */
    BigDecimal yesterdayNetReceipts(@Param("storeId") String storeId);

    /**
     * 今日实收分时间段
     *
     * @param storeId
     * @return
     */
    List<Map<String, Object>> todayTimeSlotIncome(@Param("storeId") String storeId);

    /**
     * 昨日实收分时间段
     *
     * @param storeId
     * @return
     */
    List<Map<String, Object>> yesterdayTimeSlotIncome(@Param("storeId") String storeId);

    /**
     * 菜品销售排行
     *
     * @param storeId
     * @return
     */
    List<GoodSaleParam> goodsSaleCompare(@Param("storeId") String storeId);

    /**
     * 菜品分类销售排行
     *
     * @param storeId
     * @return
     */
    List<GoodTypeSaleParam> goodsTypeSale(@Param("storeId") String storeId);

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
    Map<String,Object> weekNetReceipts(@Param("storeId")String storeId);
}
