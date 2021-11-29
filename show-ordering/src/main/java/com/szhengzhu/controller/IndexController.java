package com.szhengzhu.controller;

import com.szhengzhu.bean.ordering.GoodsSales;
import com.szhengzhu.bean.ordering.param.GoodSaleParam;
import com.szhengzhu.bean.ordering.param.GoodSaleRankParam;
import com.szhengzhu.bean.ordering.param.GoodTypeSaleParam;
import com.szhengzhu.bean.ordering.param.IndexParam;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.IndexService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping("/index")
public class IndexController {

    @Resource
    private IndexService indexService;

    @GetMapping(value = "/today/turnover")
    public Map<String, Object> todayTurnover(@RequestParam("storeId") @NotBlank String storeId) {
        return indexService.todayTurnover(storeId);
    }

    @GetMapping(value = "/week/turnover")
    public List<Map<String, Object>> weekTurnover(@RequestParam("storeId") @NotBlank String storeId) {
        return indexService.weekTurnover(storeId);
    }

    @PostMapping(value = "/today/goodsSalesRank")
    public PageGrid<GoodSaleRankParam> goodsSalesRank(@RequestBody PageParam<?> pageParam,
                                                      @RequestParam("storeId") @NotBlank String storeId) {
        return indexService.goodsSalesRank(pageParam, storeId);
    }

    @PostMapping(value = "/goodsSale/compare")
    public PageGrid<GoodSaleParam> goodsSaleCompare(@RequestBody PageParam<?> pageParam,
                                                    @RequestParam("storeId") @NotBlank String storeId) {
        return indexService.goodsSaleCompare(pageParam, storeId);
    }

    @GetMapping(value = "/goodsType/sale")
    public List<GoodTypeSaleParam> goodsTypeSale(@RequestParam("storeId") @NotBlank String storeId) {
        return indexService.goodsTypeSale(storeId);
    }

    @GetMapping(value = "/today/netReceipts")
    public IndexParam todayNetReceipts(@RequestParam("storeId") @NotBlank String storeId) {
        return indexService.netReceipts(storeId);
    }

    @GetMapping(value = "/info")
    public Map<String, Object> info(@RequestParam("storeId") @NotBlank String storeId) {
        return indexService.info(storeId);
    }

    @PostMapping(value = "/goods/sales")
    public List<GoodSaleRankParam> goodsSales(@RequestBody GoodsSales goodsSales) {
        return indexService.goodsSales(goodsSales);
    }

    @GetMapping(value = "/week/netReceipts")
    public Map<String, Object> weekNetReceipts(@RequestParam("storeId") @NotBlank String storeId) {
        return indexService.weekNetReceipts(storeId);
    }
}
