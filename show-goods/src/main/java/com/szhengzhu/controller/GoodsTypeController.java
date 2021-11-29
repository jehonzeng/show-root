package com.szhengzhu.controller;

import com.szhengzhu.bean.goods.GoodsType;
import com.szhengzhu.bean.goods.TypeSpec;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.GoodsTypeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping(value = "goodsTypes")
public class GoodsTypeController {

    @Resource
    private GoodsTypeService goodsTypeService;

    @PostMapping(value = "/add")
    public GoodsType addGoodsType(@RequestBody @Validated GoodsType base) {
        return goodsTypeService.addType(base);
    }

    @PatchMapping(value = "/edit")
    public GoodsType modifyGoodsType(@RequestBody @Validated GoodsType base) {
        return goodsTypeService.modifyGoodsType(base);
    }

    @PostMapping(value = "/page")
    public PageGrid<GoodsType> getTypePage(@RequestBody PageParam<GoodsType> base) {
        return goodsTypeService.getPage(base);
    }

    @GetMapping(value = "/combobox")
    public List<Combobox> listCombobox() {
        return goodsTypeService.listCombobox();
    }

    @PostMapping(value = "/specification/add")
    public void addTypeSpec(@RequestParam("specIds") @NotEmpty String[] specIds, @RequestParam("typeId") @NotBlank String typeId) {
        goodsTypeService.addTypeSpec(specIds, typeId);
    }

    @DeleteMapping(value = "/specification")
    public void removeTypeSpec(@RequestParam("typeId") String typeId, @RequestParam("specId") String specId) {
        goodsTypeService.removeTypeSpec(typeId, specId);
    }

    @PatchMapping(value = "/specification/modify")
    public void modifyTypeSpec(@RequestBody TypeSpec typeSpec) {
        goodsTypeService.modifyTypeSpec(typeSpec);
    }

}
