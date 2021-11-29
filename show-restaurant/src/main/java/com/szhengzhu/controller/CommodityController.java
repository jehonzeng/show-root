package com.szhengzhu.controller;

import cn.hutool.core.util.ObjectUtil;
import com.szhengzhu.client.ShowOrderingClient;
import com.szhengzhu.bean.ordering.Commodity;
import com.szhengzhu.bean.ordering.CommodityItem;
import com.szhengzhu.bean.ordering.CommodityPrice;
import com.szhengzhu.bean.ordering.CommoditySpecs;
import com.szhengzhu.bean.ordering.param.CommodityParam;
import com.szhengzhu.bean.ordering.vo.CommodityPageVo;
import com.szhengzhu.bean.ordering.vo.CommodityVo;
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
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Validated
@Api(tags = "商品：CommodityController")
@RestController
@RequestMapping("/v1/commodity")
public class CommodityController {

    @Resource
    private ShowOrderingClient showOrderingClient;

    @ApiOperation(value = "获取商品分页列表")
    @PostMapping(value = "/page")
    public Result<PageGrid<CommodityPageVo>> page(HttpServletRequest req, @RequestBody PageParam<CommodityParam> pageParam) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        CommodityParam param = ObjectUtil.isNull(pageParam.getData()) ? new CommodityParam() : pageParam.getData();
        param.setStoreId(storeId);
        pageParam.setData(param);
        return showOrderingClient.pageCommodity(pageParam);
    }

    @ApiOperation(value = "获取商品详情")
    @GetMapping(value = "/{commodityId}")
    public Result<CommodityVo> getInfo(@PathVariable("commodityId") @NotBlank String commodityId) {
        return showOrderingClient.getCommodityInfoVo(commodityId);
    }

    @ApiOperation(value = "添加商品")
    @PostMapping(value = "")
    public Result<String> add(HttpServletRequest req, @RequestBody @Validated Commodity commodity) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        commodity.setStoreId(storeId);
        commodity.setType(0);
        return showOrderingClient.addCommodity(commodity);
    }

    @ApiOperation(value = "修改商品")
    @PatchMapping(value = "")
    public Result modify(@RequestBody @Validated Commodity commodity) {
        return showOrderingClient.modifyCommodity(commodity);
    }

    @ApiOperation(value = "修改商品数量")
    @GetMapping(value = "/modify/quantity")
    public Result modifyQuantity(HttpServletRequest req, @RequestParam("commodityId") @NotBlank String commodityId,
                                         @RequestParam("quantity") @NotNull Integer quantity) {
        String employeeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_USER);
        return showOrderingClient.modifyCommodityQuantity(commodityId, employeeId, quantity);
    }

    @ApiOperation(value = "批量修改商品状态")
    @PatchMapping(value = "/batch/status/{status}")
    public Result modifyStatus(@RequestBody @NotEmpty String[] commodityIds, @PathVariable("status") @Min(-1) @Max(1) Integer status) {
        return showOrderingClient.modifyCommodityStatus(commodityIds, status);
    }

    @ApiOperation(value = "批量删除商品")
    @DeleteMapping(value = "/batch")
    public Result delete(@RequestBody @NotEmpty String[] commodityIds) {
        return showOrderingClient.modifyCommodityStatus(commodityIds, -1);
    }

    @ApiOperation(value = "获取商品列表")
    @GetMapping(value = "/list")
    public Result<List<Map<String, String>>> list(@RequestParam(value = "name", required = false) String name) {
        return showOrderingClient.listCommodity(name);
    }

    @ApiOperation(value = "添加商品价格")
    @PostMapping(value = "/price")
    public Result<String> addPrice(@RequestBody @Validated CommodityPrice commodityPrice) {
        return showOrderingClient.addCommodityPrice(commodityPrice);
    }

    @ApiOperation(value = "修改商品价格")
    @PatchMapping(value = "/price")
    public Result modifyPrice(@RequestBody @Validated CommodityPrice commodityPrice) {
        return showOrderingClient.modifyCommodityPrice(commodityPrice);
    }

    @ApiOperation(value = "删除商品商品价格")
    @DeleteMapping(value = "/price/{priceId}")
    public Result<Object> deletePrice(@PathVariable("priceId") @NotBlank String priceId) {
        return showOrderingClient.deleteCommodityPrice(priceId);
    }

    @ApiOperation(value = "添加/修改商品规格")
    @PostMapping(value = "/specs/opt")
    public Result optSpecs(@RequestBody @Validated CommoditySpecs commoditySpecs) {
        return showOrderingClient.optCommoditySpecs(commoditySpecs);
    }

    @ApiOperation(value = "添加/修改商品规格值")
    @PostMapping(value = "/specs/item/opt")
    public Result optItem(@RequestBody @Validated CommodityItem commodityItem) {
        return showOrderingClient.optCommodityItem(commodityItem);
    }

    @ApiOperation(value = "删除商品规格值")
    @DeleteMapping(value = "/specs/item")
    public Result deleteItem(@RequestParam("commodityId") @NotBlank String commodityId,
                                     @RequestParam("specsId") @NotBlank String specsId, @RequestParam("itemId") @NotBlank String itemId) {
        return showOrderingClient.deleteCommodityItem(commodityId, specsId, itemId);
    }

    @ApiOperation(value = "操作商品分类")
    @PostMapping(value = "/cate/opt/{commodityId}")
    public Result optCommodityCate(@RequestBody String[] cateIds, @PathVariable("commodityId") @NotBlank String commodityId) {
        return showOrderingClient.optCommodityCate(cateIds, commodityId);
    }

    @ApiOperation(value = "操作商品标签")
    @PostMapping(value = "/tag/opt/{commodityId}")
    public Result optCommodityTag(@RequestBody String[] tagIds, @PathVariable("commodityId") @NotBlank String commodityId) {
        return showOrderingClient.optCommodityTag(tagIds, commodityId);
    }
}
