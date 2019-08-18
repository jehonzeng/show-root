package com.szhengzhu.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.base.AreaInfo;
import com.szhengzhu.client.ShowBaseClient;
import com.szhengzhu.core.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"区域管理:AreaController"})
@RestController
@RequestMapping("/v1/areas")
public class AreaController {

    @Resource
    private ShowBaseClient showBaseClient;
    
    @ApiOperation(value = "获取地址三级列表", notes = "获取地址三级列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<List<AreaInfo>> listAll() {
        return showBaseClient.listArea();
    }
}
