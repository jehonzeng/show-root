package com.szhengzhu.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.GoodsType;
import com.szhengzhu.bean.goods.TypeSpec;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.GoodsTypeService;
import com.szhengzhu.service.SpecificationService;

@RestController
@RequestMapping(value = "goodsTypes")
public class GoodsTypeController {

    @Resource
    private GoodsTypeService goodsTypeService;
    
    @Resource
    private SpecificationService specificationService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<?> addGoodsType(@RequestBody GoodsType base) {
        return goodsTypeService.addType(base);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.PATCH)
    public Result<?> modifyGoodsType(@RequestBody GoodsType base) {
        return goodsTypeService.editType(base);
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<?> getTypePage(@RequestBody PageParam<GoodsType> base) {
        return goodsTypeService.getPage(base);
    }
    
    @RequestMapping(value = "/combobox", method = RequestMethod.GET)
    public Result<List<Combobox>> listCombobox() {
        return goodsTypeService.listCombobox();
    }
    
    @RequestMapping(value = "/specification/add", method = RequestMethod.POST)
    public Result<?> addTypeSpec(@RequestParam("specIds") String[] specIds, @RequestParam("typeId") String typeId) {
        return goodsTypeService.addTypeSpec(specIds, typeId);
    }
    
    @RequestMapping(value = "/specification", method = RequestMethod.DELETE)
    public Result<?> removeTypeSpec(@RequestParam("typeId") String typeId, @RequestParam("specId") String specId) {
        return goodsTypeService.removeTypeSpec(typeId, specId);
    }
    
    @RequestMapping(value = "/specification/modify", method = RequestMethod.PATCH)
    public Result<?> modifyTypeSpec(@RequestBody TypeSpec typeSpec) {
        return goodsTypeService.modifyTypeSpec(typeSpec);
    }

}
