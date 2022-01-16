package com.szhengzhu.controller;

import cn.hutool.core.lang.Validator;
import com.szhengzhu.bean.user.UserInfo;
import com.szhengzhu.bean.user.UserToken;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.StoreStaffVo;
import com.szhengzhu.bean.vo.UserInfoVo;
import com.szhengzhu.bean.vo.UserVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.service.UserService;
import com.szhengzhu.service.UserTokenService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

/**
 * @author Jehon Zeng
 */
@Validated
@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private UserTokenService userTokenService;

    @GetMapping(value = "/manager")
    public UserInfo getManager(@RequestParam("phone") @NotBlank String phone) {
        ShowAssert.checkTrue(!Validator.isMobile(phone), StatusCode._4000);
        return userService.getManager(phone);
    }

    @GetMapping(value = "/restaurant/manager")
    public UserInfo getRestaurantManager(@RequestParam("phone") @NotBlank String phone) {
        ShowAssert.checkTrue(!Validator.isMobile(phone), StatusCode._4000);
        return userService.getRestaurantManager(phone);
    }

    @GetMapping(value = "/manager/list")
    public List<Map<String, String>> listManager() {
        return userService.listManager();
    }

    @PostMapping(value = "/add")
    public UserInfo addUser(@RequestBody @Validated UserInfo userInfo) {
        return userService.addUser(userInfo);
    }

    @PostMapping(value = "/add/x")
    public Result<String> addXUser(@RequestBody @Validated UserInfo userInfo) {
        return new Result<>(userService.addXUser(userInfo));
    }

    @PatchMapping(value = "/modify")
    public void modifyUser(@RequestBody @Validated UserInfo userInfo) {
        userService.modifyUser(userInfo);
    }

    @PostMapping(value = "/page")
    public PageGrid<UserVo> pageUser(@RequestBody PageParam<UserVo> userPage) {
        return userService.pageUser(userPage);
    }

    @PostMapping(value = "/outrole/page")
    public PageGrid<UserVo> pageUserNotIn(@RequestBody PageParam<UserVo> userPage) {
        return userService.pageOutRole(userPage);
    }

    @GetMapping(value = "/{markId}")
    public UserInfo getUserById(@PathVariable("markId") @NotBlank String markId) {
        return userService.getUserById(markId);
    }

    @GetMapping(value = "/my/{userId}")
    public UserInfoVo getMyInfo(@PathVariable("userId") @NotBlank String userId) {
        return userService.getMyInfo(userId);
    }

    @GetMapping(value = "/w/{openId}")
    public UserInfo getInfoByOpenId(@PathVariable("openId") @NotBlank String openId) {
        return userService.getInfoByOpenId(openId);
    }

    @GetMapping(value = "/{xopenId}/x")
    public UserInfo getInfoByXopenId(@PathVariable("xopenId") @NotBlank String xopenId) {
        return userService.getInfoByXopenId(xopenId);
    }

    @GetMapping(value = "/u/{unionId}")
    public UserInfo getInfoByUnionId(@PathVariable("unionId") @NotBlank String unionId) {
        return userService.getInfoByUnionId(unionId);
    }

    @GetMapping(value = "/t/{token}")
    public UserInfo getUserByToken(@PathVariable("token") @NotBlank String token) {
        return userService.getUserByToken(token);
    }

    @GetMapping(value = "/p/{phone}")
    public UserInfo getInfoByPhone(@PathVariable("phone") @NotBlank String phone) {
        return userService.getInfoByPhone(phone);
    }

    @GetMapping(value = "/token/add")
    public UserToken addUserToken(@RequestParam("userId") @NotBlank String userId) {
        return userTokenService.addUserToken(userId);
    }

    @GetMapping(value = "/token/{token}")
    public UserToken getUserToken(@PathVariable("token") @NotBlank String token) {
        return userTokenService.getByToken(token);
    }

    @GetMapping(value = "/combobox")
    public List<Combobox> getUserList() {
        return userService.getUserList();
    }

    @GetMapping(value = "/manage/{markId}")
    public Boolean checkManage(@PathVariable(value = "markId") @NotBlank String markId) {
        return userService.checkManage(markId);
    }

    @PostMapping(value = "/instaff/page")
    public PageGrid<UserInfo> pageInStoreStaff(@RequestBody PageParam<StoreStaffVo> base) {
        return userService.pageInStoreStaff(base);
    }

    @PostMapping(value = "/outstaff/page")
    public PageGrid<UserInfo> pageOutStoreStaff(@RequestBody PageParam<StoreStaffVo> base) {
        return userService.pageOutStoreStaff(base);
    }

    @GetMapping(value = "/outmember/list")
    public List<UserInfo> listOutMember() {
        return userService.listOutMember();
    }

    @PostMapping(value = "/openid/list")
    public List<String> listWopenIdsByUserIds(@RequestBody @NotEmpty List<String> userIds) {
        return userService.listWopenIdByUserId(userIds);
    }

    @GetMapping(value = "/focus")
    public List<UserInfo> selectFocusUser() {
        return userService.selectFocusUser();
    }
}
