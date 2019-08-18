package com.szhengzhu.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.activity.SeckillInfo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.SeckillService;

@RestController
@RequestMapping("/seckill")
public class SeckillController {

    @Resource
    private SeckillService seckillService;
    
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<SeckillInfo>> pageSeckill(@RequestBody PageParam<SeckillInfo> seckillPage) {
        return seckillService.pageSeckill(seckillPage);
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<SeckillInfo> addSeckill(@RequestBody SeckillInfo seckillInfo) {
        return seckillService.saveSeckill(seckillInfo);
    }
    
    @RequestMapping(value = "/modify", method = RequestMethod.PATCH)
    public Result<SeckillInfo> modifySeckill(@RequestBody SeckillInfo seckillInfo) {
        return seckillService.updateSeckill(seckillInfo);
    }
    
    @RequestMapping(value = "/{markId}", method = RequestMethod.GET)
    private Result<SeckillInfo> getSeckillInfo(@PathVariable("markId") String markId) {
        return seckillService.getSeckillInfo(markId);
    }
}
