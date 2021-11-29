package com.szhengzhu.controller;

import cn.hutool.core.util.StrUtil;
import com.szhengzhu.bean.ordering.Commodity;
import com.szhengzhu.bean.ordering.CommodityItem;
import com.szhengzhu.bean.ordering.CommoditySpecs;
import com.szhengzhu.bean.ordering.param.CommodityParam;
import com.szhengzhu.bean.ordering.vo.CommodityPageVo;
import com.szhengzhu.bean.ordering.vo.CommodityVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.service.CommodityService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping("/commodity")
public class CommodityController {

    @Resource
    private CommodityService commodityService;

    @PostMapping(value = "/page")
    public PageGrid<CommodityPageVo> page(@RequestBody PageParam<CommodityParam> pageParam) {
        return commodityService.page(pageParam);
    }

    @GetMapping(value = "/{commodityId}")
    public Commodity getInfo(@PathVariable("commodityId") @NotBlank String commodityId) {
        return commodityService.getInfo(commodityId);
    }

    @GetMapping(value = "/vo/{commodityId}")
    public CommodityVo getInfoVo(@PathVariable("commodityId") @NotBlank String commodityId) {
        return commodityService.getInfoVo(commodityId);
    }

    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody @Validated Commodity commodity) {
        ShowAssert.checkTrue(StrUtil.isEmpty(commodity.getName()), StatusCode._4004);
        return new Result<>(commodityService.add(commodity));
    }

    @PatchMapping(value = "/modify")
    public void modify(@RequestBody @Validated Commodity commodity) {
        ShowAssert.checkTrue(StrUtil.isEmpty(commodity.getMarkId()), StatusCode._4004);
        commodityService.modify(commodity);
    }

    @GetMapping(value = "/modify/quantity")
    public void modifyQuantity(@RequestParam("commodityId") @NotBlank String commodityId, @RequestParam("employeeId") @NotBlank String employeeId,
                                         @RequestParam("quantity") @NotNull Integer quantity) {
        commodityService.modifyQuantity(commodityId, employeeId, quantity);
    }

    @PostMapping(value = "/status/{status}")
    public void modifyStatus(@RequestBody @NotEmpty String[] commodityIds, @PathVariable("status") @Min(-1) @Max(1) Integer status) {
        commodityService.modifyStatus(commodityIds, status);
    }

    @GetMapping(value = "/list/by")
    public List<Map<String, String>> list(@RequestParam(value = "name", required = false) String name) {
        return commodityService.listCommodity(name);
    }

    @RequestMapping(value = "/specs/opt", method = RequestMethod.POST)
    public void optSpecs(@RequestBody @Validated CommoditySpecs commoditySpecs) {
        commodityService.optSpecs(commoditySpecs);
    }

    @RequestMapping(value = "/specs/item/opt", method = RequestMethod.POST)
    public void optItem(@RequestBody @Validated CommodityItem commodityItem) {
        commodityService.optSpecsItem(commodityItem);
    }

    @RequestMapping(value = "/specs/item/delete", method = RequestMethod.DELETE)
    public void deleteItem(@RequestParam("commodityId") @NotBlank String commodityId, @RequestParam("specsId") @NotBlank String specsId,
                                     @RequestParam("itemId") @NotBlank String itemId) {
        commodityService.deleteSpecsItem(commodityId, specsId, itemId);
    }

    @RequestMapping(value = "/cate/opt/{commodityId}", method = RequestMethod.POST)
    public void optCommodityCate(@RequestBody String[] cateIds, @PathVariable("commodityId") @NotBlank String commodityId) {
        commodityService.optCommodityCate(commodityId, cateIds);
    }

    @RequestMapping(value = "/tag/opt/{commodityId}", method = RequestMethod.POST)
    public void optCommodityTag(@RequestBody String[] tagIds, @PathVariable("commodityId") @NotBlank String commodityId) {
        commodityService.optCommodityTag(commodityId, tagIds);
    }
}
