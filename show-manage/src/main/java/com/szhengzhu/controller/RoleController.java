package com.szhengzhu.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.base.CouponTemplate;
import com.szhengzhu.bean.order.UserCoupon;
import com.szhengzhu.bean.user.RoleInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.client.ShowBaseClient;
import com.szhengzhu.client.ShowOrderClient;
import com.szhengzhu.client.ShowUserClient;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.util.CouponUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = { "角色管理：RoleController" })
@RestController
@RequestMapping(value = "/v1/roles")
public class RoleController {

    @Resource
    private ShowUserClient showUserClient;

    @Resource
    private ShowBaseClient showBaseClient;

    @Resource
    private ShowOrderClient showOrderClient;

    @ApiOperation(value = "添加用戶角色信息", notes = "添加用戶角色信息")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result<RoleInfo> addRole(@RequestBody RoleInfo roleInfo) {
        return showUserClient.addRole(roleInfo);
    }

    @ApiOperation(value = "修改用戶角色信息", notes = "修改用戶角色信息")
    @RequestMapping(value = "", method = RequestMethod.PATCH)
    public Result<RoleInfo> modifyRole(@RequestBody RoleInfo roleInfo) {
        return showUserClient.modifyRole(roleInfo);
    }

    @ApiOperation(value = "根据主键获取角色详细信息", notes = "根据主键获取角色详细信息")
    @RequestMapping(value = "/{markId}", method = RequestMethod.GET)
    public Result<RoleInfo> getRoleInfo(
            @ApiParam(name = "markId", value = "主键", required = true) @PathVariable String markId) {
        return showUserClient.getRoleInfo(markId);
    }

    @ApiOperation(value = "获取用户角色分页列表", notes = "获取用户角色分页列表")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<RoleInfo>> pageRole(@RequestBody PageParam<RoleInfo> rolePage) {
        return showUserClient.pageRole(rolePage);
    }

    @ApiOperation(value = "获取角色列表", notes = "获取所有角色列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<List<RoleInfo>> listRole() {
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setServerStatus(true);
        return showUserClient.listRole(roleInfo);
    }

    @ApiOperation(value = "批量删除角色与用户关联关系", notes = "批量删除角色与用户关联关系")
    @RequestMapping(value = "/roleuser", method = RequestMethod.DELETE)
    public Result<?> removeUserRole(@RequestParam("userIds") String[] userIds,
            @RequestParam("roleId") String roleId) {
        return showUserClient.removeRoleUsers(roleId, userIds);
    }

    @ApiOperation(value = "批量添加角色与用户关联关系", notes = "批量添加角色与用户关联关系")
    @RequestMapping(value = "/roleuser", method = RequestMethod.POST)
    public Result<?> addRoleUsers(@RequestParam("userIds") String[] userIds,
            @RequestParam("roleId") String roleId) {
        return showUserClient.addRoleUsers(roleId, userIds);
    }

    @ApiOperation(value = "根据不同角色名称获取用户列表", notes = "根据角色名称获取用户列表")
    @RequestMapping(value = "/combobox", method = RequestMethod.GET)
    public Result<List<Combobox>> combobox(@RequestParam("roleCode") String roleCode) {
        return showUserClient.combobox(roleCode);
    }

    @ApiOperation(value = "管理员指定角色批量推送优惠券", notes = "管理员指定角色批量推送优惠券")
    @RequestMapping(value = "/coupon/send", method = RequestMethod.GET)
    public Result<?> sendCouponByRole(@RequestParam("roleId") String roleId,
            @RequestParam("templateId") String templateId) {
        CouponTemplate template = showBaseClient.getCouponTmplate(templateId);
        if (template == null)
            return new Result<>(StatusCode._5009);
        List<UserCoupon> list = CouponUtils.createCoupon(template,"");
        return  showOrderClient.sendCouponByRole(list, roleId);
    }

  
}
