package com.szhengzhu.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.StoreHouseInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.StoreHouseService;

@RestController
@RequestMapping(value = "houses")
public class StoreHouseController {

    @Resource
    private StoreHouseService storeHouseService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<?> addHouse(@RequestBody StoreHouseInfo storeHouseInfo) {
        return storeHouseService.addStoreHouse(storeHouseInfo);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.PATCH)
    public Result<?> modifyHouse(@RequestBody StoreHouseInfo storeHouseInfo) {
        return storeHouseService.modifyStoreHouse(storeHouseInfo);
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<StoreHouseInfo>> getPage(@RequestBody PageParam<StoreHouseInfo> base) {
        return storeHouseService.getPage(base);
    }
    
    @RequestMapping(value = "/combobox", method = RequestMethod.GET)
    public Result<List<Combobox>> listCombobox() {
        return storeHouseService.listCombobox();
    }
    
    @RequestMapping(value = "/{markId}", method = RequestMethod.GET)
    public Result<?> getHouseInfo(@PathVariable("markId") String markId){
        return storeHouseService.getHouseInfo(markId);
    }

}
