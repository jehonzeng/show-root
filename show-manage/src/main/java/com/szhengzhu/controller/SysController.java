package com.szhengzhu.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.base.SysInfo;
import com.szhengzhu.client.ShowBaseClient;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.service.WechatService;
import com.szhengzhu.util.StringUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"配置管理：SysController"})
@RestController
@RequestMapping("/v1/sys")
public class SysController {

    @Resource
    private ShowBaseClient showBaseClient;
    
    @Resource
    private WechatService wechatService;
    
    @ApiOperation(value = "根据名称获取数据", notes = "根据名称获取数据")
    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public Result<String> getByName(@PathVariable("name") String name) {
        return showBaseClient.getByName(name);
    }
    
    @ApiOperation(value = "根据名称修改数据", notes = "根据名称修改数据")
    @RequestMapping(value = "", method = RequestMethod.PATCH)
    public Result<?> modifySys(@RequestBody SysInfo sysInfo) {
        return showBaseClient.modifySys(sysInfo);
    }
    
    @ApiOperation(value = "将数据刷新到微信平台", notes = "将数据刷新到微信平台")
    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public Result<?> refreshMenu(@RequestParam("name") String name) {
        Result<String> jsonResult = showBaseClient.getByName(name);
        if (!jsonResult.getCode().equals("200")) 
            return jsonResult;
        if (StringUtils.isEmpty(jsonResult.getData())) 
            return new Result<>(StatusCode._4008);
        return wechatService.refreshMenu(jsonResult.getData());
    }
}
