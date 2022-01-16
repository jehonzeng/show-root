package com.szhengzhu.controller;

import com.szhengzhu.feign.ShowOrderingClient;
import com.szhengzhu.bean.ordering.Store;
import com.szhengzhu.bean.ordering.param.StoreParam;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * @author Administrator
 */
@Validated
@Api(tags = "门店：StoreController")
@RestController
@RequestMapping("/v1/store")
public class StoreController {

    @Resource
    private ShowOrderingClient showOrderingClient;

    @ApiOperation(value = "获取门店分页列表")
    @PostMapping(value = "/page")
    public Result<PageGrid<Store>> page(@RequestBody PageParam<StoreParam> pageParam) {
        return showOrderingClient.pageStore(pageParam);
    }

    @ApiOperation(value = "获取门店详细信息")
    @GetMapping(value = "/{storeId}")
    public Result<Store> getInfo(@PathVariable("storeId") @NotBlank String storeId) {
        return showOrderingClient.getStoreInfo(storeId);
    }

    @ApiOperation(value = "添加门店")
    @PostMapping(value = "")
    public Result<String> add(@RequestBody @Validated Store store) {
        return showOrderingClient.addStore(store);
    }

    @ApiOperation(value = "修改门店信息")
    @PatchMapping(value = "")
    public Result modify(@RequestBody @Validated Store store) {
        return showOrderingClient.modifyStore(store);
    }

    @ApiOperation(value = "删除门店")
    @DeleteMapping(value = "/{storeId}")
    public Result delete(@PathVariable("storeId") @NotBlank String storeId) {
        return showOrderingClient.deleteStore(storeId);
    }
}
