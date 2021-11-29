package com.szhengzhu.controller;

import cn.hutool.core.util.ObjectUtil;
import com.szhengzhu.bean.ordering.Specs;
import com.szhengzhu.bean.ordering.SpecsItem;
import com.szhengzhu.bean.ordering.vo.SpecsVo;
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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Validated
@Api(tags = "规格：SpecsController")
@RestController
@RequestMapping("/v1/specs")
public class SpecsController {

    @Resource
    private ShowOrderingClient showOrderingClient;

    @ApiOperation(value = "获取规格主信息分页列表")
    @PostMapping(value = "/page")
    public Result<PageGrid<Specs>> page(HttpServletRequest req, @RequestBody PageParam<Specs> pageParam) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        Specs param = ObjectUtil.isNull(pageParam.getData()) ? new Specs() : pageParam.getData();
        param.setStoreId(storeId);
        pageParam.setData(param);
        return showOrderingClient.pageSpecs(pageParam);
    }

    @ApiOperation(value = "添加规格主信息")
    @PostMapping(value = "")
    public Result add(HttpServletRequest req, @RequestBody @Validated Specs specs) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        specs.setStoreId(storeId);
        return showOrderingClient.addSpecs(specs);
    }

    @ApiOperation(value = "修改规格主信息")
    @PatchMapping(value = "")
    public Result modify(@RequestBody @Validated Specs specs) {
        return showOrderingClient.modifySpecs(specs);
    }

    @ApiOperation(value = "批量修改规格主信息状态")
    @PatchMapping(value = "/batch/status/{status}")
    public Result modifyStatus(@RequestBody @NotEmpty String[] specsIds, @PathVariable("status") @Min(-1) @Max(1) Integer status) {
        return showOrderingClient.modifySpecsStatus(specsIds, status);
    }

    @ApiOperation(value = "批量删除规格主信息")
    @DeleteMapping(value = "/batch")
    public Result delete(@RequestBody @NotEmpty String[] specsIds) {
        return showOrderingClient.modifySpecsStatus(specsIds, -1);
    }

    @ApiOperation(value = "获取已启用规格主信息键值对列表")
    @GetMapping(value = "/combobox")
    public Result<List<SpecsVo>> getCombobox(HttpServletRequest req) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        return showOrderingClient.getSpecsCombobox(storeId);
    }

    @ApiOperation(value = "获取分类规格键值对列表")
    @PostMapping(value = "/combobox/cate")
    public Result<List<SpecsVo>> getComboboxByCateId(HttpServletRequest req, @RequestBody(required = false) String[] cateIds) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        return showOrderingClient.getSpecsComboboxByCateId(cateIds, storeId);
    }

    @ApiOperation(value = "获取规格列表")
    @GetMapping(value = "/list/by")
    public Result<List<Map<String, String>>> list(@RequestParam(value = "name", required = false) String name) {
        return showOrderingClient.listSpecs(name);
    }

    @ApiOperation(value = "获取规格子信息分页列表")
    @PostMapping(value = "/item/page")
    public Result<PageGrid<SpecsItem>> pageItem(@RequestBody PageParam<SpecsItem> pageParam) {
        return showOrderingClient.pageSpecsItem(pageParam);
    }

    @ApiOperation(value = "添加规格子信息")
    @PostMapping(value = "/item")
    public Result addItem(@RequestBody @Validated SpecsItem specsItem) {
        return showOrderingClient.addSpecsItem(specsItem);
    }

    @ApiOperation(value = "修改规格子信息")
    @PatchMapping(value = "/item")
    public Result modifyItem(@RequestBody @Validated SpecsItem specsItem) {
        return showOrderingClient.modifySpecsItem(specsItem);
    }

    @ApiOperation(value = "批量修改规格子信息")
    @PatchMapping(value = "/item/batch/status/{status}")
    public Result modifyItemStatus(@RequestBody @NotEmpty String[] itemIds, @PathVariable("status") @Min(-1) @Max(1) Integer status) {
        return showOrderingClient.modifySpecsItemStatus(itemIds, status);
    }

    @ApiOperation(value = "批量删除规格子信息")
    @DeleteMapping(value = "/item/batch")
    public Result deleteItem(@RequestBody @NotEmpty String[] itemIds) {
        return showOrderingClient.modifySpecsItemStatus(itemIds, -1);
    }
}
