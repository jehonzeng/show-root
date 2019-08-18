package com.szhengzhu.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.SpecificationInfo;
import com.szhengzhu.bean.vo.SpecBatchVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.SpecificationService;

@RestController
@RequestMapping(value = "specifications")
public class SpecificationController {

    @Resource
    private SpecificationService specificationService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<?> addSpecification(@RequestBody SpecificationInfo base) {
        return specificationService.addSpecification(base);
    }

    @RequestMapping(value = "/modify", method = RequestMethod.PATCH)
    public Result<?> modifySpecification(@RequestBody SpecificationInfo base) {
        return specificationService.editSpecification(base);
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<SpecificationInfo>> getSpecificationPage(
            @RequestBody PageParam<SpecificationInfo> base) {
        return specificationService.getPage(base);
    }
    
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Result<?> listSpecification(@RequestParam("goodsId") String goodsId) {
        return specificationService.getSpecList(goodsId);
    }
    
    @RequestMapping(value = "/addBatch",method = RequestMethod.POST)
    public Result<?> addBatchSpecification(@RequestBody SpecBatchVo base) {
        return specificationService.insertBatchSpec(base);
    }
    
    @RequestMapping(value = "/page/in", method = RequestMethod.POST)
    public Result<PageGrid<SpecificationInfo>> pageInByType(@RequestBody PageParam<SpecificationInfo> base) {
        return specificationService.pageInByType(base);
    }
    
    @RequestMapping(value = "/page/notin", method = RequestMethod.POST)
    public Result<PageGrid<SpecificationInfo>> pageSpecNotInByType(@RequestBody PageParam<SpecificationInfo> base) {
        return specificationService.pageNotInByType(base);
    }
}
