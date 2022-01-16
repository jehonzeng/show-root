package com.szhengzhu.controller;

import com.szhengzhu.feign.ShowOrderClient;
import com.szhengzhu.feign.ShowUserClient;
import com.szhengzhu.bean.user.RoleInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Administrator
 */
@Api(tags = { "角色管理：RoleController" })
@RestController
@RequestMapping(value = "/v1/roles")
public class RoleController {

    @Resource
    private ShowUserClient showUserClient;

    @Resource
    private ShowOrderClient showOrderClient;

    @ApiOperation(value = "添加用戶角色信息", notes = "添加用戶角色信息")
    @PostMapping(value = "")
    public Result<RoleInfo> addRole(@RequestBody RoleInfo roleInfo) {
        return showUserClient.addRole(roleInfo);
    }

    @ApiOperation(value = "修改用戶角色信息", notes = "修改用戶角色信息")
    @PatchMapping(value = "")
    public Result<RoleInfo> modifyRole(@RequestBody RoleInfo roleInfo) {
        return showUserClient.modifyRole(roleInfo);
    }

    @ApiOperation(value = "根据主键获取角色详细信息", notes = "根据主键获取角色详细信息")
    @GetMapping(value = "/{markId}")
    public Result<RoleInfo> getRoleInfo(
            @ApiParam(name = "markId", value = "主键", required = true) @PathVariable("markId") @NotBlank String markId) {
        return showUserClient.getRoleInfo(markId);
    }

    @ApiOperation(value = "获取用户角色分页列表", notes = "获取用户角色分页列表")
    @PostMapping(value = "/page")
    public Result<PageGrid<RoleInfo>> pageRole(@RequestBody PageParam<RoleInfo> rolePage) {
        return showUserClient.pageRole(rolePage);
    }

    @ApiOperation(value = "获取角色列表", notes = "获取所有角色列表")
    @GetMapping(value = "/list")
    public Result<List<RoleInfo>> listRole() {
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setServerStatus(true);
        return showUserClient.listRole(roleInfo);
    }

    @ApiOperation(value = "批量删除角色与用户关联关系", notes = "批量删除角色与用户关联关系")
    @DeleteMapping(value = "/roleuser")
    public Result removeUserRole(@RequestParam("userIds") String[] userIds,
            @RequestParam("roleId") String roleId) {
        return showUserClient.removeRoleUsers(roleId, userIds);
    }

    @ApiOperation(value = "批量添加角色与用户关联关系", notes = "批量添加角色与用户关联关系")
    @PostMapping(value = "/roleuser")
    public Result addRoleUsers(@RequestParam("userIds") String[] userIds,
            @RequestParam("roleId") String roleId) {
        return showUserClient.addRoleUsers(roleId, userIds);
    }

    @ApiOperation(value = "根据不同角色名称获取用户列表", notes = "根据角色名称获取用户列表")
    @GetMapping(value = "/combobox")
    public Result<List<Combobox>> combobox(@RequestParam("roleCode") String roleCode) {
        return showUserClient.combobox(roleCode);
    }

    @ApiOperation(value = "管理员指定角色批量推送优惠券", notes = "管理员指定角色批量推送优惠券")
    @GetMapping(value = "/coupon/send")
    public Result<?> sendCouponByRole(@RequestParam("roleId") String roleId,
            @RequestParam("templateId") String templateId) {
        return  showOrderClient.sendCouponByRole(roleId, templateId);
    }


}
