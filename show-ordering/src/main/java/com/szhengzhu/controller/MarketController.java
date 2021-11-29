package com.szhengzhu.controller;

import com.szhengzhu.bean.ordering.MarketInfo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.MarketService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping("/market")
public class MarketController {

    @Resource
    private MarketService marketService;

    @PostMapping(value = "/page")
    public PageGrid<MarketInfo> page(@RequestBody PageParam<MarketInfo> pageParam) {
        return marketService.page(pageParam);
    }

    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody @Validated MarketInfo marketInfo) {
        return new Result<>(marketService.add(marketInfo));
    }

    @PatchMapping(value = "/modify")
    public void modify(@RequestBody @Validated MarketInfo marketInfo) {
        marketService.modify(marketInfo);
    }

    @DeleteMapping(value = "/{marketId}")
    public void delete(@PathVariable("marketId") @NotBlank String marketId) {
        marketService.delete(marketId);
    }
}
