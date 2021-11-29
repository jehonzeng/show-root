package com.szhengzhu.controller;

import com.szhengzhu.bean.rpt.SaleParam;
import com.szhengzhu.bean.rpt.SaleStatistics;
import com.szhengzhu.service.RptService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 报表统计类
 *
 * @author Jehon Zeng
 */
@RestController
@RequestMapping("/rpt")
public class RptController {

    @Resource
    private RptService rptService;

    @GetMapping(value = "/sale/statistic")
    public List<SaleStatistics> rptSaleStatistic(@RequestParam("month") String month,
                                                         @RequestParam("partner") String partner) {
        return rptService.rptSaleStatistics(month, partner);
    }

    @GetMapping(value = "/year/sale")
    public List<Map<String, Object>> rptYearSale() {
        return rptService.rptYearSale();
    }

    @GetMapping(value = "/city/delivery/count")
    public List<Map<String, Object>> rptCityDeliveryCount() {
        return rptService.rptCityDeliveryCount();
    }

    @PostMapping(value = "/sale/volume")
    public List<Map<String, Object>> rptSaleVolume(@RequestBody SaleParam param) {
        return rptService.rptSaleVolume(param);
    }
}
