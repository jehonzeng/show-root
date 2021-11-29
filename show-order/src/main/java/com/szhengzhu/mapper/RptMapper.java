package com.szhengzhu.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.szhengzhu.bean.rpt.SaleInfo;
import com.szhengzhu.bean.rpt.SaleParam;
import com.szhengzhu.bean.rpt.SaleStatistics;

/**
 * @author Jehon Zeng
 */
public interface RptMapper {

    List<SaleStatistics> rptSaleStatistics(@Param("month") String month, @Param("partner") String partner);
    
    SaleInfo rptYearSale(@Param("month") String month);
    
    List<Map<String, Object>> rptSaleVolume(SaleParam param);
    
    List<Map<String, Object>> rptCityDeliveryCount();
}
