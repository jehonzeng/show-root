package com.szhengzhu.controller;

import com.szhengzhu.client.ShowBaseClient;
import com.szhengzhu.bean.base.PacksInfo;
import com.szhengzhu.bean.base.PacksItem;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.PacksVo;
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
@Api(tags = {"券礼包:PacksController"})
@RestController
@RequestMapping(value = "/v1/packs")
public class PacksController {

    @Resource
    private ShowBaseClient showBaseClient;

    @ApiOperation(value = "添加礼包信息", notes = "添加礼包信息")
    @PostMapping(value = "")
    public Result<PacksInfo> addPacks(@RequestBody PacksInfo base) {
        return showBaseClient.addPacks(base);
    }

    @ApiOperation(value = "修改礼包信息", notes = "修改礼包信息")
    @PatchMapping(value = "")
    public Result<PacksInfo> modifyPacks(@RequestBody PacksInfo base) {
        return showBaseClient.modifyPacks(base);
    }

    @ApiOperation(value = "礼包信息分页", notes = "礼包信息分页")
    @PostMapping(value = "/page")
    public Result<PageGrid<PacksInfo>> packsPage(@RequestBody PageParam<PacksInfo> base) {
        return showBaseClient.packsPage(base);
    }

    @ApiOperation(value = "礼包券列表分页", notes = "礼包券列表分页")
    @PostMapping(value = "/item/page")
    public Result<PageGrid<PacksVo>> packsItemPage(@RequestBody PageParam<PacksItem> base) {
        return showBaseClient.packsItemPage(base);
    }

    @ApiOperation(value = "礼包批量添加券模板", notes = "礼包批量添加券模板")
    @PostMapping(value = "/item/batch")
    public Result batchPacksTemplate(@RequestBody BatchVo base) {
        return showBaseClient.batchPacksTemplate(base);
    }

    @ApiOperation(value = "获取礼包信息", notes = "过去礼包信息")
    @GetMapping(value = "/{markId}")
    public Result<PacksInfo> getPacksInfo(@PathVariable("markId") @NotBlank String markId) {
        return showBaseClient.getPacksInfo(markId);
    }

    @ApiOperation(value = "礼包券修改", notes = "礼包券修改")
    @PatchMapping(value = "/item")
    public Result<PacksItem> updatePacksTeplate(@RequestBody PacksItem base) {
        return showBaseClient.updatePacksTeplate(base);
    }
}
