package com.szhengzhu.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.DeliveryArea;
import com.szhengzhu.bean.goods.StoreHouseInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.client.ShowBaseClient;
import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.common.Commons;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "仓库管理：StoreHouseController" })
@RestController
@RequestMapping(value = "/v1/houses")
public class StoreHouseController {

    @Resource
    private ShowGoodsClient showGoodsClient;
    
    @Resource
    private ShowBaseClient showBaseClient;

    @ApiOperation(value = "添加仓库信息", notes = "添加品仓库信息")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result<StoreHouseInfo> addHouse(@RequestBody StoreHouseInfo storeHouseInfo) {
        return showGoodsClient.addHouse(storeHouseInfo);
    }
    
    @ApiOperation(value = "根据id查询仓库信息", notes = "根据id查询仓库信息")
    @RequestMapping(value = "/{markId}", method = RequestMethod.GET)
    public Result<StoreHouseInfo> getHouseInfo(@PathVariable("markId") String markId) {
        return showGoodsClient.getHouseInfo(markId);
    }

    @ApiOperation(value = "修改仓库信息", notes = "修改仓库信息")
    @RequestMapping(value = "", method = RequestMethod.PATCH)
    public Result<StoreHouseInfo> modifyHouse(@RequestBody StoreHouseInfo storeHouseInfo) {
        return showGoodsClient.modifyHouse(storeHouseInfo);
    }

    @ApiOperation(value = "仓库分页信息列表", notes = "根据不同条件获取仓库分页信息列表")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<StoreHouseInfo>> housePage(@RequestBody PageParam<StoreHouseInfo> base) {
        return showGoodsClient.getHousePage(base);
    }

    @ApiOperation(value = "添加仓库配送范围信息", notes = "添加仓库配送范围信息")
    @RequestMapping(value = "/delivery", method = RequestMethod.POST)
    public Result<DeliveryArea> addDeliveryArea(HttpSession session ,@RequestBody DeliveryArea base) {
        String userId = (String) session.getAttribute(Commons.SESSION);
        base.setCreator(userId);
        return showGoodsClient.addDeliveryAreaInfo(base);
    }

    @ApiOperation(value = "修改仓库配送范围信息", notes = "修改仓库配送范围信息")
    @RequestMapping(value = "/delivery", method = RequestMethod.PATCH)
    public Result<DeliveryArea> modifyDeliveryArea(@RequestBody DeliveryArea base) {
        return showGoodsClient.editDeliveryAreaInfo(base);
    }

    @ApiOperation(value = "仓库配送范围分页信息列表", notes = "根据不同条件获取仓库配送范围分页信息列表")
    @RequestMapping(value = "/delivery/page", method = RequestMethod.POST)
    public Result<PageGrid<DeliveryArea>> deliveryAreaPage(
            @RequestBody PageParam<DeliveryArea> base) {
        return showGoodsClient.getDeliveryAreaPage(base);
    }
    
    @ApiOperation(value = "根据id获取配送区域信息", notes = "根据id获取配送区域信息")
    @RequestMapping(value = "/delivery/{markId}",method = RequestMethod.GET)
    public Result<DeliveryArea> deliveryAreaInfo(@PathVariable("markId") String markId){
        return showGoodsClient.deliveryAreaInfo(markId);
    }
    
    @ApiOperation(value = "删除配送区域信息", notes = "删除配送区域信息")
    @RequestMapping(value = "/delivery/{markId}", method = RequestMethod.DELETE)
    public Result<?> deleteDelivery(@PathVariable("markId") String markId) {
        return showGoodsClient.deleteDelivery(markId);
    }
    
    @ApiOperation(value = "获取仓库下拉列表", notes = "获取仓库下拉列表")
    @RequestMapping(value = "/combobox", method = RequestMethod.GET)
    public Result<List<Combobox>> listCombobox() {
        return showGoodsClient.listHouseCombobox();
    }
    
    @ApiOperation(value = "获取配送省份下拉列表", notes = "获取配送省份下拉列表")
    @RequestMapping(value = "/provinceList", method = RequestMethod.GET)
    public Result<List<Combobox>> listProvinceCombobox() {
        return showBaseClient.listProvince();
    }
    
    @ApiOperation(value = "批量添加省份下所有配送区域", notes = "批量添加省份下所有配送区域")
    @RequestMapping(value ="/delivery/batch",method=RequestMethod.POST)
    public Result<?> addBatchByProvince(@RequestBody DeliveryArea base) {
        return showGoodsClient.addBatchByProvince(base);
    }
}
