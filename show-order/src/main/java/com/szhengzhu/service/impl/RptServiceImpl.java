package com.szhengzhu.service.impl;

import com.szhengzhu.bean.rpt.SaleInfo;
import com.szhengzhu.bean.rpt.SaleParam;
import com.szhengzhu.bean.rpt.SaleStatistics;
import com.szhengzhu.mapper.RptMapper;
import com.szhengzhu.service.RptService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author Jehon Zeng
 */
@Service("rptService")
public class RptServiceImpl implements RptService {
    
    @Resource
    private RptMapper rptMapper;

    @Override
    public List<SaleStatistics> rptSaleStatistics(String month, String partner) {
        return rptMapper.rptSaleStatistics(month, partner);
    }

    @Override
    public List<Map<String, Object>> rptYearSale() {
        int monthSize = 12;
        List<Map<String, Object>> saleList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -12);
        for (int index = 0; index < monthSize; index++) {
            Map<String, Object> monthMap = new HashMap<>(2);
            calendar.add(Calendar.MONTH, 1);
            int month = calendar.get(Calendar.MONTH) + 1;
            int year = calendar.get(Calendar.YEAR);
            SaleInfo yearSale = rptMapper.rptYearSale(year + "-" + (month > 9? month : "0" + month));
            monthMap.put("month", month);
            monthMap.put("list", yearSale);
            saleList.add(monthMap);
        }
        return saleList;
    }
    
    @Override
    public List<Map<String, Object>> rptCityDeliveryCount() {
        return rptMapper.rptCityDeliveryCount();
    }

    @Override
    public List<Map<String, Object>> rptSaleVolume(SaleParam param) {
        return rptMapper.rptSaleVolume(param);
    }
}
