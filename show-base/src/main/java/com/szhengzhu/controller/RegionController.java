package com.szhengzhu.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.base.RegionInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.RegionService;

@RestController
@RequestMapping("/regions")
public class RegionController {

    @Resource
    private RegionService regionService;
    
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<?> pageRegion(@RequestBody PageParam<RegionInfo> base ) {
        return regionService.getRegionPage(base);
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<?> addRegion(@RequestBody RegionInfo base ) {
        return regionService.addRegion(base);
    }
    
    @RequestMapping(value = "/edit", method = RequestMethod.PATCH)
    public Result<?> editRegion(@RequestBody RegionInfo base ) {
        return regionService.editRegion(base);
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<List<Combobox>> listRegion(){
        return regionService.getComboboxList();
    }
}
