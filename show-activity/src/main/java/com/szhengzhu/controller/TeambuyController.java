package com.szhengzhu.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.activity.TeambuyInfo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.TeambuyService;

@RestController
@RequestMapping("/teambuy")
public class TeambuyController {

    @Resource
    private TeambuyService teambuyService;
    
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<TeambuyInfo>> pageTeambuy(@RequestBody PageParam<TeambuyInfo> teambuyPage) {
        return teambuyService.pageTeambuy(teambuyPage);
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<TeambuyInfo> addTeambuy(@RequestBody TeambuyInfo teambuyInfo) {
        return teambuyService.saveTeambuy(teambuyInfo);
    }
    
    @RequestMapping(value = "/modify", method = RequestMethod.PATCH)
    public Result<TeambuyInfo> modifyTeambuy(@RequestBody TeambuyInfo teambuyInfo) {
        return teambuyService.updateTeambuy(teambuyInfo);
    }
    
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Result<TeambuyInfo> getTeambuyInfo(@RequestParam("markId") String markId, @RequestParam("specIds") String specIds) {
        return teambuyService.getTeambuyInfo(markId, specIds);
    }
}
