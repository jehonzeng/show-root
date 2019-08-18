package com.szhengzhu.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.CategoryInfo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.CategoryService;

@RestController
@RequestMapping(value = "categorys")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<?> addCategory(@RequestBody CategoryInfo categoryInfo) {
        return categoryService.addCategory(categoryInfo);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.PATCH)
    public Result<?> modifyCategory(@RequestBody CategoryInfo categoryInfo) {
        return categoryService.editCategory(categoryInfo);
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<CategoryInfo>> getPage(@RequestBody PageParam<CategoryInfo> base) {
        return categoryService.getPage(base);
    }

    @RequestMapping(value = "/{markId}", method = RequestMethod.GET)
    public Result<?> getInfo(@PathVariable("markId") String markId) {
        return categoryService.getCategoryInfo(markId);
    }

    @RequestMapping(value = "/downList", method = RequestMethod.GET)
    public Result<?> getDwonList(
            @RequestParam(value = "serverStatus", required = false) String serverStatus) {
        return categoryService.getDownList(serverStatus);
    }
    
    @RequestMapping(value = "/superList", method = RequestMethod.GET)
    public Result<?> getSuperList(){
        return categoryService.getSuperList();
    }
}
