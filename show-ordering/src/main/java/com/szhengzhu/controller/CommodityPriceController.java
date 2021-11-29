package com.szhengzhu.controller;

import com.szhengzhu.bean.ordering.CommodityPrice;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.CommodityPriceService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * @author jehon
 */
@Validated
@RestController
@RequestMapping("/commodity/price")
public class CommodityPriceController {

    @Resource
    private CommodityPriceService commodityPriceService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<String> addPrice(@RequestBody @Validated CommodityPrice commodityPrice) {
        return new Result<>(commodityPriceService.addPrice(commodityPrice));
    }

    @RequestMapping(value = "/modify", method = RequestMethod.PATCH)
    public void modifyPrice(@RequestBody @Validated CommodityPrice commodityPrice) {
        commodityPriceService.modifyPrice(commodityPrice);
    }

    @RequestMapping(value = "/{priceId}", method = RequestMethod.DELETE)
    public void deletePrice(@PathVariable("priceId") @NotBlank String priceId) {
        commodityPriceService.deletePrice(priceId);
    }
}
