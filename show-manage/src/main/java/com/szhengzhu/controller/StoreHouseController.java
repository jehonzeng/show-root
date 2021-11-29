package com.szhengzhu.controller;

import com.szhengzhu.client.ShowBaseClient;
import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.bean.goods.DeliveryArea;
import com.szhengzhu.bean.goods.StoreHouseInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Administrator
 */
@Validated
@Api(tags = {"仓库管理：StoreHouseController"})
@RestController
@RequestMapping(value = "/v1/houses")
public class StoreHouseController {

    @Resource
    private ShowGoodsClient showGoodsClient;

    @Resource
    private ShowBaseClient showBaseClient;

    @ApiOperation(value = "添加仓库信息", notes = "添加品仓库信息")
    @PostMapping(value = "")
    public Result<StoreHouseInfo> addHouse(@RequestBody @Validated StoreHouseInfo storeHouseInfo) {
        return showGoodsClient.addHouse(storeHouseInfo);
    }

    @ApiOperation(value = "根据id查询仓库信息", notes = "根据id查询仓库信息")
    @GetMapping(value = "/{markId}")
    public Result<StoreHouseInfo> getHouseInfo(@PathVariable("markId") @NotBlank String markId) {
        return showGoodsClient.getHouseInfo(markId);
    }

    @ApiOperation(value = "修改仓库信息", notes = "修改仓库信息")
    @PatchMapping(value = "")
    public Result<StoreHouseInfo> modifyHouse(@RequestBody @Validated StoreHouseInfo storeHouseInfo) {
        return showGoodsClient.modifyHouse(storeHouseInfo);
    }

    @ApiOperation(value = "仓库分页信息列表", notes = "根据不同条件获取仓库分页信息列表")
    @PostMapping(value = "/page")
    public Result<PageGrid<StoreHouseInfo>> housePage(@RequestBody PageParam<StoreHouseInfo> base) {
        return showGoodsClient.getHousePage(base);
    }

    @ApiOperation(value = "添加仓库配送范围信息", notes = "添加仓库配送范围信息")
    @PostMapping(value = "/delivery")
    public Result<DeliveryArea> addDeliveryArea(HttpSession session, @RequestBody @Validated DeliveryArea base) {
        String userId = (String) session.getAttribute(Contacts.LJS_SESSION);
        base.setCreator(userId);
        return showGoodsClient.addDeliveryAreaInfo(base);
    }

    @ApiOperation(value = "修改仓库配送范围信息", notes = "修改仓库配送范围信息")
    @PatchMapping(value = "/delivery")
    public Result<DeliveryArea> modifyDeliveryArea(@RequestBody @Validated DeliveryArea base) {
        return showGoodsClient.editDeliveryAreaInfo(base);
    }

    @ApiOperation(value = "仓库配送范围分页信息列表", notes = "根据不同条件获取仓库配送范围分页信息列表")
    @PostMapping(value = "/delivery/page")
    public Result<PageGrid<DeliveryArea>> deliveryAreaPage(
            @RequestBody PageParam<DeliveryArea> base) {
        return showGoodsClient.getDeliveryAreaPage(base);
    }

    @ApiOperation(value = "根据id获取配送区域信息", notes = "根据id获取配送区域信息")
    @GetMapping(value = "/delivery/{markId}")
    public Result<DeliveryArea> deliveryAreaInfo(@PathVariable("markId") @NotBlank String markId) {
        return showGoodsClient.deliveryAreaInfo(markId);
    }

    @ApiOperation(value = "删除配送区域信息", notes = "删除配送区域信息")
    @DeleteMapping(value = "/delivery/{markId}")
    public Result<?> deleteDelivery(@PathVariable("markId") @NotBlank String markId) {
        return showGoodsClient.deleteDelivery(markId);
    }

    @ApiOperation(value = "获取仓库下拉列表", notes = "获取仓库下拉列表")
    @GetMapping(value = "/combobox")
    public Result<List<Combobox>> listCombobox() {
        return showGoodsClient.listHouseCombobox();
    }

    @ApiOperation(value = "获取配送省份下拉列表", notes = "获取配送省份下拉列表")
    @GetMapping(value = "/provinceList")
    public Result<List<Combobox>> listProvinceCombobox() {
        return showBaseClient.listProvince();
    }

    @ApiOperation(value = "批量添加省份下所有配送区域", notes = "批量添加省份下所有配送区域")
    @PostMapping(value = "/delivery/batch")
    public Result addBatchByProvince(HttpSession session, @RequestBody DeliveryArea base) {
        String userId = (String) session.getAttribute(Contacts.LJS_SESSION);
        base.setCreator(userId);
        return showGoodsClient.addBatchByProvince(base);
    }

    @ApiOperation(value = "制定仓库下启用指定省份所有配送地区", notes = "指定仓库下启用指定省份所有配送地区")
    @PostMapping(value = "/delivery/enabled")
    public Result enabledDeliveryArea(@RequestBody DeliveryArea base) {
        return showGoodsClient.enabledDeliveryArea(base);
    }
}
