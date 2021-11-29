package com.szhengzhu.service;

import com.szhengzhu.bean.rpt.SaleParam;
import com.szhengzhu.bean.rpt.SaleStatistics;

import java.util.List;
import java.util.Map;

/**
 * @author Jehon Zeng
 */
public interface RptService {

    /**
     * 获取商品月销量报表
     * 
     * @date 2019年9月6日 下午3:14:18
     * @param month 月份
     * @param partner 合作商代码
     * @return
     */
    List<SaleStatistics> rptSaleStatistics(String month, String partner);
    
    /**
     * 近半年销量
     * 
     * @date 2019年9月9日 下午5:18:12
     * @return
     */
    List<Map<String, Object>> rptYearSale();
    
    /**
     * 各城市配送订单数量
     * 
     * @date 2019年9月10日 下午5:31:12
     * @return
     */
    List<Map<String, Object>> rptCityDeliveryCount();
    
    /**
     * 获取商品销量
     * 
     * @date 2019年9月16日 下午3:14:25
     * @param param
     * @return
     */
    List<Map<String, Object>> rptSaleVolume(SaleParam param);
}
