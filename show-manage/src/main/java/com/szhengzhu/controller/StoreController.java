package com.szhengzhu.controller;

import com.szhengzhu.feign.ShowBaseClient;
import com.szhengzhu.feign.ShowUserClient;
import com.szhengzhu.bean.base.StoreInfo;
import com.szhengzhu.bean.base.StoreItem;
import com.szhengzhu.bean.user.UserInfo;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.StoreStaffVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 */
@Validated
@Api(tags = {"门店管理：StoreController"})
@RestController
@RequestMapping(value = "/v1/stores")
public class StoreController {

    @Resource
    private ShowBaseClient showBaseClient;

    @Resource
    private ShowUserClient showUserClient;

    @ApiOperation(value = "获取门店信息分页", notes = "获取门店信息分页")
    @PostMapping(value = "/page")
    public Result<PageGrid<StoreInfo>> pageStore(@RequestBody PageParam<StoreInfo> base) {
        return showBaseClient.pageStore(base);
    }

    @ApiOperation(value = "添加门店信息", notes = "添加门店信息")
    @PostMapping(value = "")
    public Result<StoreInfo> addStore(@RequestBody @Validated StoreInfo base) {
        return showBaseClient.addStore(base);
    }

    @ApiOperation(value = "编辑门店信息", notes = "编辑门店信息")
    @PatchMapping(value = "")
    public Result<StoreInfo> editStore(@RequestBody @Validated StoreInfo base) {
        return showBaseClient.editStore(base);
    }

    @ApiOperation(value = "获取门店下拉表", notes = "获取门店下拉表")
    @GetMapping(value = "/combobox")
    public Result<List<Combobox>> listStore() {
        return showBaseClient.listStore();
    }

    @ApiOperation(value = "获取门店人员列表", notes = "获取门店人员列表")
    @PostMapping(value = "/user/page")
    public Result<PageGrid<UserInfo>> pageStoreItem(@RequestBody PageParam<StoreStaffVo> base) {
        return showUserClient.pageInStoreStaff(base);
    }

    @ApiOperation(value = "获取不在门店的人员列表", notes = "获取不在门店的人员列表")
    @PostMapping(value = "/user/notIn")
    public Result<PageGrid<UserInfo>> pageStoreNotIn(@RequestBody PageParam<StoreStaffVo> base) {
        return showUserClient.pageOutStoreStaff(base);
    }

    @ApiOperation(value = "批量添加门店人员", notes = "批量门店人员")
    @PostMapping(value = "/item/batch")
    public Result<Object> addItem(@RequestBody BatchVo base) {
        return showBaseClient.addStoreStaff(base);
    }

    @ApiOperation(value = "删除门店人员", notes = "删除门店人员")
    @PostMapping(value = "/item/delete")
    public Result deleteItem(@RequestBody StoreItem base) {
        return showBaseClient.deleteStoreStaff(base);
    }
}
