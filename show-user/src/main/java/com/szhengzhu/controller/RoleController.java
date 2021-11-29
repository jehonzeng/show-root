package com.szhengzhu.controller;

import com.szhengzhu.bean.user.RoleInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.UserBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.RoleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author Jehon Zeng
 */
@Validated
@RestController
@RequestMapping("/roles")
public class RoleController {

    @Resource
    private RoleService roleService;

    @PostMapping(value = "/add")
    public RoleInfo addRole(@RequestBody @Validated RoleInfo roleInfo) {
        return roleService.saveRole(roleInfo);
    }

    @PatchMapping(value = "/modify")
    public RoleInfo modifyRole(@RequestBody @Validated RoleInfo roleInfo) {
        return roleService.updateRole(roleInfo);
    }

    @GetMapping(value = "/{markId}")
    public RoleInfo getRoleInfo(@PathVariable("markId") @NotBlank String markId) {
        return roleService.getRoleById(markId);
    }

    @PostMapping(value = "/page")
    public PageGrid<RoleInfo> pageRole(@RequestBody PageParam<RoleInfo> rolePage) {
        return roleService.pageRole(rolePage);
    }

    @PostMapping(value = "/list")
    public List<RoleInfo> listRole(@RequestBody RoleInfo roleInfo) {
        return roleService.listRole(roleInfo);
    }

    @DeleteMapping(value = "/roleuser")
    public void removeRoleUsers(@RequestParam("roleId") @NotBlank String roleId, @RequestParam("userIds") @NotEmpty String[] userIds) {
        roleService.deleteRoleUsers(roleId, userIds);
    }

    @PostMapping(value = "/roleuser")
    public void addRoleUsers(@RequestParam("roleId") @NotBlank String roleId, @RequestParam("userIds") @NotEmpty String[] userIds) {
        roleService.saveRoleUsers(roleId, userIds);
    }

    @GetMapping(value = "/combobox")
    public List<Combobox> downList(@RequestParam("roleCode") @NotBlank String roleCode) {
        return roleService.getListByCode(roleCode);
    }

    @GetMapping(value = "/usersByRole")
    public List<UserBase> getUsersByRole(@RequestParam("roleId") @NotBlank String roleId) {
        return roleService.getListByRole(roleId);
    }
}
