package com.szhengzhu.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.user.PartnerInfo;
import com.szhengzhu.client.ShowUserClient;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"合作商信息管理：FoodsController"})
@RestController
@RequestMapping("/v1/partners")
public class PartnerController {

    @Resource
    private ShowUserClient showUserClient;
    
    @ApiOperation(value = "录入合作商信息", notes = "录入合作商信息")
    @RequestMapping(value="",method=RequestMethod.POST)
    Result<?> addPartner(@RequestBody PartnerInfo base){
        return showUserClient.addPartner(base);
    }
    
    @ApiOperation(value = "编辑合作商信息", notes = "编辑合作商信息")
    @RequestMapping(value="",method=RequestMethod.PATCH)
    Result<?> editPartner(@RequestBody PartnerInfo base){
        return showUserClient.editPartner(base);
    }
    
    @ApiOperation(value = "获取合作商分页列表", notes = "获取合作商分页列表")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    Result<PageGrid<PartnerInfo>> editPartner(@RequestBody PageParam<PartnerInfo> base){
        return showUserClient.getPartnerPage(base);
    }
    
    @ApiOperation(value = "删除合作商信息", notes = "删除合作商信息")
    @RequestMapping(value="/{markId}",method=RequestMethod.DELETE)
    public Result<?> deletePartner(@PathVariable("markId") String markId){
        return showUserClient.deletePartner(markId);
    }
}
