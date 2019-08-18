package com.szhengzhu.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.user.RoleInfo;
import com.szhengzhu.bean.vo.UserBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.RoleService;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Resource
    private RoleService roleService;
    
    @RequestMapping(value ="/add", method = RequestMethod.POST)
    public Result<RoleInfo> addRole(@RequestBody RoleInfo roleInfo) {
        return roleService.saveRole(roleInfo);
    }
    
    @RequestMapping(value = "/modify", method = RequestMethod.PATCH)
    public Result<RoleInfo> modifyRole(@RequestBody RoleInfo roleInfo) {
        return roleService.updateRole(roleInfo);
    }
    
    @RequestMapping(value = "/{markId}", method = RequestMethod.GET)
    public Result<RoleInfo> getRoleInfo(@PathVariable(value = "markId") String markId) {
        return roleService.getRoleById(markId);
    }
    
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<RoleInfo>> pageRole(@RequestBody PageParam<RoleInfo> rolePage) {
        return roleService.pageRole(rolePage);
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result<List<RoleInfo>> listRole(@RequestBody RoleInfo roleInfo) {
        return roleService.listRole(roleInfo);
    }
    
    @RequestMapping(value = "/roleuser", method = RequestMethod.DELETE)
    public Result<?> removeRoleUsers(@RequestParam("roleId") String roleId, @RequestParam("userIds") String[] userIds) {
        return roleService.deleteRoleUsers(roleId, userIds);
    }
    
    @RequestMapping(value = "/roleuser", method = RequestMethod.POST)
    public Result<?> addRoleUsers(@RequestParam("roleId") String roleId, @RequestParam("userIds") String[] userIds) {
        return roleService.saveRoleUsers(roleId, userIds);
    }
    
    @RequestMapping(value = "/combobox", method = RequestMethod.GET)
    public Result<?> downList(@RequestParam("roleCode") String roleCode) {
        return roleService.getListByCode(roleCode);
    }
    
    @RequestMapping(value = "/usersByRole", method = RequestMethod.GET)
    public List<UserBase> getUsersByRole(@RequestParam("roleId") String roleId) {
        return roleService.getListByRole(roleId);
    }
}
