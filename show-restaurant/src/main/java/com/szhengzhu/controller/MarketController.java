package com.szhengzhu.controller;

import cn.hutool.core.util.ObjectUtil;
import com.szhengzhu.bean.ordering.MarketInfo;
import com.szhengzhu.client.ShowOrderingClient;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

/**
 * @author Administrator
 */
@Validated
@Api(tags = "买减活动：MarketController")
@RestController
@RequestMapping("/v1/market")
public class MarketController {

    @Resource
    private ShowOrderingClient showOrderingClient;

    @ApiOperation(value = "获取买减活动分页列表")
    @PostMapping(value = "/page")
    public Result<PageGrid<MarketInfo>> page(HttpServletRequest req, @RequestBody PageParam<MarketInfo> pageParam) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        MarketInfo param = ObjectUtil.isNull(pageParam.getData()) ? new MarketInfo() : pageParam.getData();
        param.setStoreId(storeId);
        pageParam.setData(param);
        return showOrderingClient.pageMarket(pageParam);
    }

    @ApiOperation(value = "新增买减活动")
    @PostMapping(value = "")
    public Result<String> add(HttpServletRequest req, @RequestBody @Validated MarketInfo marketInfo) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        String employeeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_USER);
        marketInfo.setStoreId(storeId);
        marketInfo.setCreator(employeeId);
        return showOrderingClient.addMarket(marketInfo);
    }

    @ApiOperation(value = "修改买减活动")
    @PatchMapping(value = "")
    public Result modify(@RequestBody @Validated MarketInfo marketInfo) {
        return showOrderingClient.modifyMarket(marketInfo);
    }

    @ApiOperation(value = "删除买减活动")
    @DeleteMapping(value = "/{marketId}")
    public Result delete(@PathVariable("marketId") @NotBlank String marketId) {
        return showOrderingClient.deleteMarket(marketId);
    }
}
