package com.szhengzhu.controller;

import com.szhengzhu.bean.goods.CategoryInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.CategoryService;
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
@RequestMapping(value = "categorys")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @PostMapping(value = "/add")
    public CategoryInfo addCategory(@RequestBody @Validated CategoryInfo categoryInfo) {
        return categoryService.addCategory(categoryInfo);
    }

    @PatchMapping(value = "/edit")
    public CategoryInfo modifyCategory(@RequestBody @Validated CategoryInfo categoryInfo) {
        return categoryService.editCategory(categoryInfo);
    }

    @PostMapping(value = "/page")
    public PageGrid<CategoryInfo> getPage(@RequestBody PageParam<CategoryInfo> base) {
        return categoryService.getPage(base);
    }

    @GetMapping(value = "/{markId}")
    public CategoryInfo getInfo(@PathVariable("markId") @NotBlank String markId) {
        return categoryService.getCategoryInfo(markId);
    }

    @GetMapping(value = "/downList")
    public List<Combobox> getDwonList(
            @RequestParam(value = "serverStatus", required = false) String serverStatus) {
        return categoryService.getDownList(serverStatus);
    }
}
