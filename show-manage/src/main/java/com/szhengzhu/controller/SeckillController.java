package com.szhengzhu.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.activity.SeckillInfo;
import com.szhengzhu.client.ShowActivityClient;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"秒杀活动管理：SeckillController"})
@RestController
@RequestMapping("/v1/seckill")
public class SeckillController {

    @Resource
    private ShowActivityClient showActivityClient;
    
    @ApiOperation(value = "获取秒杀活动分页列表", notes = "获取秒杀活动分页列表")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<SeckillInfo>> pageSeckill(@RequestBody PageParam<SeckillInfo> seckillPage) {
        return showActivityClient.pageSeckill(seckillPage);
    }
    
    @ApiOperation(value = "添加秒杀活动", notes = "添加秒杀活动")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result<SeckillInfo> addSeckill(@RequestBody SeckillInfo seckillInfo) {
        return showActivityClient.addSeckill(seckillInfo);
    }
    
    @ApiOperation(value = "修改秒杀活动", notes = "修改秒杀活动")
    @RequestMapping(value = "", method = RequestMethod.PATCH)
    public Result<SeckillInfo> modifySeckill(@RequestBody SeckillInfo seckillInfo) {
        return showActivityClient.modifySeckill(seckillInfo);
    }
}
