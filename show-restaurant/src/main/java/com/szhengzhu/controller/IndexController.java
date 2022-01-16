package com.szhengzhu.controller;

import com.szhengzhu.feign.ShowOrderingClient;
import com.szhengzhu.bean.ordering.GoodsSales;
import com.szhengzhu.bean.ordering.param.GoodSaleParam;
import com.szhengzhu.bean.ordering.param.GoodSaleRankParam;
import com.szhengzhu.bean.ordering.param.GoodTypeSaleParam;
import com.szhengzhu.bean.ordering.param.IndexParam;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Validated
@Api(tags = "首页：IndexController")
@RestController
@RequestMapping("/v1/index")
public class IndexController {

    @Resource
    private ShowOrderingClient showOrderingClient;

    @ApiOperation(value = "查询今日的营业额")
    @GetMapping(value = "/today/turnover")
    public Result<Map<String, Object>> getTodayTurnover(HttpServletRequest req) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        return showOrderingClient.todayTurnover(storeId);
    }

    @ApiOperation(value = "查询近7天的营业额")
    @GetMapping(value = "/week/turnover")
    public Result<List<Map<String, Object>>> getWeekTurnover(HttpServletRequest req) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        return showOrderingClient.weekTurnover(storeId);
    }

    @ApiOperation(value = "查询菜品销售排行")
    @PostMapping(value = "/today/goodsSalesRank")
    public Result<PageGrid<GoodSaleRankParam>> getTodaySaleRank(HttpServletRequest req, @RequestBody PageParam<?> param) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        return showOrderingClient.goodsSalesRank(param, storeId);
    }

    @ApiOperation(value = "菜品销量排行")
    @PostMapping(value = "/goodsSale/compare")
    public Result<PageGrid<GoodSaleParam>> goodsSaleCompare(HttpServletRequest req, @RequestBody PageParam<?> param) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        return showOrderingClient.goodsSaleCompare(param, storeId);
    }

    @ApiOperation(value = "菜品分类销售排行")
    @GetMapping(value = "/goodsType/sale")
    public Result<List<GoodTypeSaleParam>> goodsTypeSale(HttpServletRequest req) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        return showOrderingClient.goodsTypeSale(storeId);
    }

    @ApiOperation(value = "实收 ")
    @GetMapping(value = "/today/netReceipts")
    public Result<IndexParam> netReceipts(HttpServletRequest req) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        return showOrderingClient.netReceipts(storeId);
    }

    @ApiOperation(value = "买单 (昨日买单量，昨日人数)")
    @GetMapping(value = "/info")
    public Result<Map<String, Object>> info(HttpServletRequest req) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        return showOrderingClient.info(storeId);
    }

    @ApiOperation(value = "查询菜品销售")
    @PostMapping(value = "/goods/sales")
    public Result<List<GoodSaleRankParam>> getTodaySale(HttpServletRequest req, @RequestBody GoodsSales goodsSales) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        goodsSales.setStoreId(storeId);
        return showOrderingClient.goodsSales(goodsSales);
    }

    @ApiOperation(value = "查询本周和上周实收")
    @GetMapping(value = "/week/netReceipts")
    public Result<Map<String, Object>> weekNetReceipts(HttpServletRequest req) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        return showOrderingClient.weekNetReceipts(storeId);
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        //转换日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
