package com.szhengzhu.controller;

import com.szhengzhu.feign.ShowOrderingClient;
import com.szhengzhu.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * @author Administrator
 */
@Validated
@Api(tags = { "分类：CategoryController" })
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private ShowOrderingClient showOrderingClient;

    @ApiOperation(value = "获取分类及分类商品列表")
    @GetMapping(value = "/list")
    public Result listLjsCate(@RequestParam("storeId") @NotBlank String storeId) {
        return showOrderingClient.listLjsCate(storeId);
    }
}
