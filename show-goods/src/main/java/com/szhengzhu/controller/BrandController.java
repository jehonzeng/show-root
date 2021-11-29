package com.szhengzhu.controller;

import com.szhengzhu.bean.goods.BrandInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.BrandService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping(value = "brands")
public class BrandController {

    @Resource
    private BrandService brandService;

    @PostMapping(value = "/add")
    public BrandInfo addBrand(@RequestBody @Validated BrandInfo brandInfo) {
        return brandService.addGoodsBrand(brandInfo);
    }

    @PatchMapping(value = "/edit")
    public BrandInfo modifyBrand(@RequestBody @Validated BrandInfo brandInfo) {
        return brandService.editGoodsBrand(brandInfo);
    }

    @PostMapping(value = "/page")
    public PageGrid<BrandInfo> getPage(@RequestBody PageParam<BrandInfo> base) {
        return brandService.getPage(base);
    }

    @GetMapping(value = "/{markId}")
    public BrandInfo getInfo(@PathVariable("markId") @NotBlank String markId) {
        return brandService.getBrandInfo(markId);
    }
    
    @GetMapping(value = "/combobox")
    public List<Combobox> listCombobox() {
        return brandService.listCombobox();
    }
}
