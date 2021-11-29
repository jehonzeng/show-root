package com.szhengzhu.controller;

import com.szhengzhu.bean.ordering.Category;
import com.szhengzhu.bean.ordering.CategoryCommodity;
import com.szhengzhu.bean.ordering.CategorySpecs;
import com.szhengzhu.bean.ordering.vo.CategoryVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.xwechat.vo.CategoryModel;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.CategoryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @PostMapping(value = "/page")
    public PageGrid<CategoryVo> page(@RequestBody PageParam<Category> pageParam) {
        return categoryService.page(pageParam);
    }

    @PostMapping(value = "/add")
    public void add(@RequestBody @Validated Category category) {
        categoryService.add(category);
    }

    @PatchMapping(value = "/modify")
    public void modify(@RequestBody @Validated Category category) {
        categoryService.modify(category);
    }

    @PatchMapping(value = "/batch/status/{status}")
    public void modifyStatus(@RequestBody @NotEmpty String[] cateIds, @PathVariable("status") @Min(-1) @Max(1) Integer status) {
        categoryService.modifyStatus(cateIds, status);
    }

    @GetMapping(value = "/combobox")
    public List<Combobox> listComboboxVo(@RequestParam("storeId") @NotBlank String storeId) {
        return categoryService.listCombobox(storeId);
    }

    @PatchMapping(value = "/specs/modify")
    public void modifyCateSpecs(@RequestBody @Validated CategorySpecs categorySpecs) {
        categoryService.modifyCateSpecs(categorySpecs);
    }

    @PostMapping(value = "/specs/batch/opt/{cateId}")
    public void optCateSpecs(@RequestBody String[] specsIds, @PathVariable("cateId") @NotBlank String cateId) {
        categoryService.optCateSpecs(specsIds, cateId);
    }

    @PatchMapping(value = "/commodity/modify")
    public void modifyCateCommodity(@RequestBody @Validated CategoryCommodity categoryCommodity) {
        categoryService.modifyCateCommodity(categoryCommodity);
    }

    @DeleteMapping(value = "/commodity/batch/opt/{cateId}")
    public void optCateCommodity(@RequestBody String[] commodityIds, @PathVariable("cateId") @NotBlank String cateId) {
        categoryService.optCateCommodity(cateId, commodityIds);
    }

    @GetMapping(value = "/list/res")
    public List<CategoryModel> listResCate(@RequestParam("storeId") @NotBlank String storeId) {
        return categoryService.listResCate(storeId);
    }

    @GetMapping(value = "/list/x")
    public List<CategoryModel> listLjsCate(@RequestParam("storeId") @NotBlank String storeId) {
        return categoryService.listLjsCate(storeId);
    }
}
