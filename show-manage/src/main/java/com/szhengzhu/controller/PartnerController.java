package com.szhengzhu.controller;

import com.szhengzhu.feign.ShowUserClient;
import com.szhengzhu.bean.user.PartnerInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Administrator
 */
@Validated
@Api(tags = {"合作商信息管理：FoodsController"})
@RestController
@RequestMapping("/v1/partners")
public class PartnerController {

    @Resource
    private ShowUserClient showUserClient;

    @ApiOperation(value = "录入合作商信息", notes = "录入合作商信息")
    @PostMapping(value = "")
    Result<PartnerInfo> addPartner(@RequestBody PartnerInfo base) {
        return showUserClient.addPartner(base);
    }

    @ApiOperation(value = "编辑合作商信息", notes = "编辑合作商信息")
    @PatchMapping(value = "")
    Result<PartnerInfo> editPartner(@RequestBody PartnerInfo base) {
        return showUserClient.editPartner(base);
    }

    @ApiOperation(value = "获取合作商分页列表", notes = "获取合作商分页列表")
    @PostMapping(value = "/page")
    Result<PageGrid<PartnerInfo>> editPartner(@RequestBody PageParam<PartnerInfo> base) {
        return showUserClient.getPartnerPage(base);
    }

    @ApiOperation(value = "删除合作商信息", notes = "删除合作商信息")
    @DeleteMapping(value = "/{markId}")
    public Result deletePartner(@PathVariable("markId") @NotBlank String markId) {
        return showUserClient.deletePartner(markId);
    }

    @ApiOperation(value = "获取合作商下拉列表", notes = "获取合作商下拉列表")
    @GetMapping(value = "/combobox")
    public Result<List<Combobox>> listCombobox() {
        return showUserClient.listPartnerCombobox();
    }
}
