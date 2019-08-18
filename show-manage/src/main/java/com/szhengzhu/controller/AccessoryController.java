package com.szhengzhu.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.AccessoryInfo;
import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"附属品管理:AccessoryController"})
@RestController
@RequestMapping(value="/v1/accessorys")
public class AccessoryController {

    @Resource
    private ShowGoodsClient showGoodsClient;
    
    @ApiOperation(value ="添加附属品信息", notes="添加附属品信息")
    @RequestMapping(value = "", method = RequestMethod.POST)
    Result<?> addAccessory(@RequestBody AccessoryInfo base){
        return showGoodsClient.addAccessory(base);
    }

    @ApiOperation(value ="获取附属品分页列表", notes="获取附属品信息分页列表")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    Result<PageGrid<AccessoryInfo>> accessoryPage(@RequestBody PageParam<AccessoryInfo> base){
        return showGoodsClient.accessoryPage(base);
    }

    @ApiOperation(value ="编辑附属品信息", notes="编辑附属品信息")
    @RequestMapping(value = "", method = RequestMethod.PATCH)
    Result<AccessoryInfo> editAccessory(@RequestBody AccessoryInfo base){
        return showGoodsClient.editAccessory(base);
    }

    @ApiOperation(value ="获取编辑需要的附属品信息", notes="获取编辑需要的附属品信息")
    @RequestMapping(value = "/{markId}", method = RequestMethod.GET)
    Result<AccessoryInfo> getAccessoryInfo(@PathVariable("markId") String markId){
        return showGoodsClient.getAccessoryInfo(markId);
    }
}
