package com.szhengzhu.controller;

import com.szhengzhu.bean.base.CounselInfo;
import com.szhengzhu.bean.base.ProductContent;
import com.szhengzhu.bean.base.ProductInfo;
import com.szhengzhu.bean.vo.NewsVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.WebsiteService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
@RequestMapping(value = "webs")
public class WebsiteController {

    @Resource
    private WebsiteService websiteService;

    @PostMapping(value = "/product/page")
    public PageGrid<ProductInfo> getProductPage(@RequestBody PageParam<ProductInfo> base) {
        return websiteService.getProductPage(base);
    }

    @PostMapping(value = "/product/add")
    public ProductInfo addProduct(@RequestBody @Validated ProductInfo base) {
        return websiteService.addProductInfo(base);
    }

    @GetMapping(value = "/product/{markId}")
    public ProductInfo getProductInfo(@PathVariable("markId") @NotBlank String markId) {
        return websiteService.getProductInfo(markId);
    }

    @PatchMapping(value = "/product/edit")
    public ProductInfo editProduct(@RequestBody @Validated ProductInfo base) {
        return websiteService.editProductInfo(base);
    }

    @GetMapping(value = "/content/{productId}")
    public ProductContent getProductContent(@PathVariable("productId") @NotBlank String productId) {
        return websiteService.getContent(productId);
    }

    @PatchMapping(value = "/content/edit")
    public ProductContent editProductContent(@RequestBody @NotBlank ProductContent base) {
        return websiteService.editContent(base);
    }

    @PostMapping(value = "/counsel/page")
    public PageGrid<CounselInfo> getCounselPage(@RequestBody PageParam<CounselInfo> base) {
        return websiteService.getCounselPage(base);
    }

    @PostMapping(value = "/counsel/add")
    public void addCounsel(@RequestBody @NotBlank CounselInfo base) {
        websiteService.addConselInfo(base);
    }

    @GetMapping(value = "/product/goodsList")
    public List<ProductInfo> getGoodsList() {
        return websiteService.getGoodsList();
    }

    @GetMapping(value = "/product/newsList")
    public PageGrid<ProductInfo> getNewList(@RequestParam("pageNo") @NotNull Integer pageNo,
            @RequestParam("pageSize") @NotNull Integer pageSize) {
        return websiteService.getNewList(pageNo, pageSize);
    }

    @GetMapping(value = "/product/newsInfo")
    public NewsVo getNewsInfo(@RequestParam("markId") @NotBlank String markId) {
        return websiteService.getNewsDetail(markId);
    }
}
