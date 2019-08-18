package com.szhengzhu.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.activity.TeambuyInfo;
import com.szhengzhu.client.ShowActivityClient;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"团购活动管理：TeambuyController"})
@RestController
@RequestMapping("/v1/teambuy")
public class TeambuyController {

    @Resource
    private ShowActivityClient showActivityClient;
    
    @ApiOperation(value = "获取团购活动分页列表", notes = "获取团购活动分页列表")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<TeambuyInfo>> pageTeambuy(@RequestBody PageParam<TeambuyInfo> teambuyPage) {
        return showActivityClient.pageTeambuy(teambuyPage);
    }
    
    @ApiOperation(value = "添加团购活动", notes = "添加团购活动")
    @RequestMapping(value = "", method = RequestMethod.POST)
    private Result<TeambuyInfo> addTeambuy(@RequestBody TeambuyInfo teambuyInfo) {
        return showActivityClient.addTeambuy(teambuyInfo);
    }
    
    @ApiOperation(value = "修改团购活动", notes = "修改团购活动")
    @RequestMapping(value = "", method = RequestMethod.PATCH)
    private Result<TeambuyInfo> modifyTeambuy(@RequestBody TeambuyInfo teambuyInfo) {
        return showActivityClient.modifyTeambuy(teambuyInfo);
    }
}
