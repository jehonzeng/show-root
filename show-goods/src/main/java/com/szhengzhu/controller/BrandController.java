package com.szhengzhu.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.BrandInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.BrandService;

@RestController
@RequestMapping(value = "brands")
public class BrandController {

    @Resource
    private BrandService brandService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<?> addBrand(@RequestBody BrandInfo brandInfo) {
        return brandService.addGoodsBrand(brandInfo);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.PATCH)
    public Result<?> modifyBrand(@RequestBody BrandInfo brandInfo) {
        return brandService.editGoodsBrand(brandInfo);
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<BrandInfo>> getPage(@RequestBody PageParam<BrandInfo> base) {
        return brandService.getPage(base);
    }

    @RequestMapping(value = "/{markId}", method = RequestMethod.GET)
    public Result<?> getInfo(@PathVariable("markId") String markId) {
        return brandService.getBrandInfo(markId);
    }
    
    @RequestMapping(value = "/combobox", method = RequestMethod.GET)
    public Result<List<Combobox>> listCombobox() {
        return brandService.listCombobox();
    }
}
