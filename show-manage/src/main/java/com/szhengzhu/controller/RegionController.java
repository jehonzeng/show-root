
package com.szhengzhu.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.base.RegionInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.client.ShowBaseClient;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"门店管理：RegionController"})
@RestController
@RequestMapping(value="/v1/regions")
public class RegionController {
    
    @Resource
    private ShowBaseClient showBaseClient;
    
    @ApiOperation(value="获取门店区域信息分页",notes="获取门店区域信息分页")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<RegionInfo>> pageRegion(@RequestBody PageParam<RegionInfo> base ) {
        return showBaseClient.pageRegion(base);
    }
    
    @ApiOperation(value="添加门店区域信息",notes="添加门店区域信息")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result<RegionInfo> addRegion(@RequestBody RegionInfo base ) {
        return showBaseClient.addRegion(base);
    }
    
    @ApiOperation(value="编辑门店区域信息",notes="编辑门店区域信息")
    @RequestMapping(value = "", method = RequestMethod.PATCH)
    public Result<RegionInfo> editRegion(@RequestBody RegionInfo base ) {
        return showBaseClient.editRegion(base);
    }
    
    @ApiOperation(value="获取区域下拉表",notes="获取区域下拉表")
    @RequestMapping(value = "/combobox", method = RequestMethod.GET)
    public Result<List<Combobox>> listRegion() {
        return showBaseClient.listRegion();
    }
}
