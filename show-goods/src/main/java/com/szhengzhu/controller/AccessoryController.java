package com.szhengzhu.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.AccessoryInfo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.AccessoryService;

@RestController
@RequestMapping(value = "accessorys")
public class AccessoryController {
    
    @Resource
    private AccessoryService accessoryService;
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<?> save(@RequestBody AccessoryInfo base) {
        return accessoryService.addAccessory(base);
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<AccessoryInfo>> page(@RequestBody PageParam<AccessoryInfo> base) {
        return accessoryService.getAccessoryPage(base);
    }

    @RequestMapping(value = "/modify", method = RequestMethod.PATCH)
    public Result<?> edit(@RequestBody AccessoryInfo base) {
        return accessoryService.editAccessory(base);
    }
    
    @RequestMapping(value = "/{markId}", method = RequestMethod.GET)
    public Result<?> getAccessoryInfo(@PathVariable("markId") String markId) {
        return accessoryService.selectAccessoryById(markId);
    }

}
