package com.szhengzhu.controller;

import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.bean.goods.AccessoryInfo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * @author terry shi
 */
@Validated
@Api(tags = {"附属品管理:AccessoryController"})
@RestController
@RequestMapping(value = "/v1/accessorys")
public class AccessoryController {

    @Resource
    private ShowGoodsClient showGoodsClient;

    @ApiOperation(value = "添加附属品信息", notes = "添加附属品信息")
    @PostMapping(value = "")
    public Result<AccessoryInfo> addAccessory(@RequestBody @Validated AccessoryInfo base) {
        return showGoodsClient.addAccessory(base);
    }

    @ApiOperation(value = "获取附属品分页列表", notes = "获取附属品信息分页列表")
    @PostMapping(value = "/page")
    public Result<PageGrid<AccessoryInfo>> accessoryPage(@RequestBody PageParam<AccessoryInfo> base) {
        return showGoodsClient.accessoryPage(base);
    }

    @ApiOperation(value = "编辑附属品信息", notes = "编辑附属品信息")
    @PatchMapping(value = "")
    public Result<AccessoryInfo> editAccessory(@RequestBody @Validated AccessoryInfo base) {
        return showGoodsClient.editAccessory(base);
    }

    @ApiOperation(value = "获取编辑需要的附属品信息", notes = "获取编辑需要的附属品信息")
    @GetMapping(value = "/{markId}")
    public Result<AccessoryInfo> getAccessoryInfo(@PathVariable("markId") @NotBlank String markId) {
        return showGoodsClient.getAccessoryInfo(markId);
    }
}
