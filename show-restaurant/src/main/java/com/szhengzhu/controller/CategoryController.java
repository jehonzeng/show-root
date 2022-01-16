package com.szhengzhu.controller;

import cn.hutool.core.util.ObjectUtil;
import com.szhengzhu.feign.ShowOrderingClient;
import com.szhengzhu.bean.ordering.Category;
import com.szhengzhu.bean.ordering.CategoryCommodity;
import com.szhengzhu.bean.ordering.CategorySpecs;
import com.szhengzhu.bean.ordering.vo.CategoryVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.xwechat.vo.CategoryModel;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author Administrator
 */
@Validated
@Api(tags = "分类：CategoryController")
@RestController
@RequestMapping("/v1/category")
public class CategoryController {

    @Resource
    private ShowOrderingClient showOrderingClient;

    @ApiOperation(value = "获取分类分页列表")
    @PostMapping(value = "/page")
    public Result<PageGrid<CategoryVo>> page(HttpServletRequest req, @RequestBody PageParam<Category> pageParam) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        Category param = ObjectUtil.isNull(pageParam.getData()) ? new Category() : pageParam.getData();
        param.setStoreId(storeId);
        pageParam.setData(param);
        return showOrderingClient.pageCate(pageParam);
    }

    @ApiOperation(value = "添加分类信息")
    @PostMapping(value = "")
    public Result add(HttpServletRequest req, @RequestBody @Validated Category category) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        category.setStoreId(storeId);
        return showOrderingClient.addCate(category);
    }

    @ApiOperation(value = "修改分类信息")
    @PatchMapping(value = "")
    public Result modify(@RequestBody @Validated Category category) {
        return showOrderingClient.modifyCate(category);
    }

    @ApiOperation(value = "批量修改分类状态")
    @PatchMapping(value = "/batch/status/{status}")
    public Result modifyStatus(@RequestBody @NotEmpty String[] cateIds, @PathVariable("status") @Min(-1) @Max(1) Integer status) {
        return showOrderingClient.modifyCateStatus(cateIds, status);
    }

    @ApiOperation(value = "批量删除分类信息")
    @DeleteMapping(value = "/batch")
    public Result deleteItem(@RequestBody @NotEmpty String[] cateIds) {
        return showOrderingClient.modifyCateStatus(cateIds, -1);
    }

    @ApiOperation(value = "点餐时获取分类及分类商品列表", notes = "点餐时获取商品分类及列表")
    @GetMapping(value = "/res/list")
    public Result<List<CategoryModel>> listResCate(HttpServletRequest req) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        return showOrderingClient.listResCate(storeId);
    }

    @ApiOperation(value = "获取分类键值对列表")
    @GetMapping(value = "/combobox")
    public Result<List<Combobox>> getCombobox(HttpServletRequest req) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        return showOrderingClient.getCateCombobox(storeId);
    }

    @ApiOperation(value = "批量添加或删除分类规格关联关系", notes = "specsIds为空时，即为删除")
    @PostMapping(value = "/specs/batch/opt/{cateId}")
    public Result optCateSpecs(@RequestBody String[] specsIds, @PathVariable("cateId") @NotBlank String cateId) {
        return showOrderingClient.optCateSpecs(specsIds, cateId);
    }

    @ApiOperation(value = "修改分类规格关联关系")
    @PatchMapping(value = "/specs")
    public Result modifyCateSpecs(@RequestBody @Validated CategorySpecs categorySpecs) {
        return showOrderingClient.modifyCateSpecs(categorySpecs);
    }

    @ApiOperation(value = "批量添加分类商品")
    @PostMapping(value = "/commodity/batch/opt/{cateId}")
    public Result optCateCommodity(@RequestBody String[] commodityIds, @PathVariable("cateId") @NotBlank String cateId) {
        return showOrderingClient.optCateCommodity(commodityIds, cateId);
    }

    @ApiOperation(value = "修改分类关联关系")
    @PatchMapping(value = "/commodity")
    public Result<Object> modifyCateCommodity(@RequestBody @Validated CategoryCommodity categoryCommodity) {
        return showOrderingClient.modifyCateCommodity(categoryCommodity);
    }
}
