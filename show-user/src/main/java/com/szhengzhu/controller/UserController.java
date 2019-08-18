package com.szhengzhu.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.user.UserInfo;
import com.szhengzhu.bean.user.UserIntegral;
import com.szhengzhu.bean.user.UserToken;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.IntegralVo;
import com.szhengzhu.bean.vo.UserVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.service.UserIntegralService;
import com.szhengzhu.service.UserService;
import com.szhengzhu.service.UserTokenService;
import com.szhengzhu.util.StringUtils;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Resource
    private UserService userService;
    
    @Resource
    private UserIntegralService userIntegralService;
    
    @Resource
    private UserTokenService userTokenService;
    
    @RequestMapping(value = "/manager", method = RequestMethod.GET)
    public Result<UserInfo> getManager(@RequestParam("phone") String phone) {
        if (StringUtils.isEmpty(phone))
            return new Result<>(StatusCode._4004);
        return userService.getManager(phone);
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<UserInfo> addUser(@RequestBody UserInfo userInfo) {
        return userService.addUser(userInfo);
    }
    
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<UserVo>> pageUser(@RequestBody PageParam<UserVo> userPage) {
        return userService.pageUser(userPage);
    }
    
    @RequestMapping(value = "/page/notin", method = RequestMethod.POST)
    public Result<PageGrid<UserVo>> pageUserNotIn(@RequestBody PageParam<UserVo> userPage) {
        return userService.pageUserNotIn(userPage);
    }
    
    @RequestMapping(value = "/{markId}", method = RequestMethod.GET)
    public Result<UserInfo> getUserById(@PathVariable("markId") String markId) {
        return userService.getUserById(markId);
    }
    
    @RequestMapping(value = "/integraltotal/page", method = RequestMethod.POST)
    public Result<PageGrid<IntegralVo>> pageIntegralTotal(@RequestBody PageParam<Map<String, String>> integralPage) {
        return userIntegralService.pageIntegralTotal(integralPage);
    }
    
    @RequestMapping(value = "/integral/page", method = RequestMethod.POST)
    public Result<PageGrid<UserIntegral>> pageIntegral(PageParam<UserIntegral> integralPage) {
        return userIntegralService.pageIntegral(integralPage);
    }
    
    @RequestMapping(value = "/integral", method = RequestMethod.POST)
    public Result<?> addIntegral(@RequestBody UserIntegral integral) {
        if (integral == null || StringUtils.isEmpty(integral.getUserId()) || integral.getIntegralLimit() == null)
            return new Result<>(StatusCode._4004);
        return userIntegralService.add(integral);
    }
    
    @RequestMapping(value = "/info/openid", method = RequestMethod.GET)
    public Result<UserInfo> getUserByOpenId(@RequestParam("openId") String openId) {
        return userService.getUserByOpenId(openId);
    }
    
    @RequestMapping(value = "/info/token", method = RequestMethod.GET)
    public Result<UserInfo> getUserByToken(@RequestParam("token") String token) {
        if (StringUtils.isEmpty(token))
            return new Result<>(StatusCode._4004);
        return userService.getUserByToken(token);
    }
    
    @RequestMapping(value = "/token/add", method = RequestMethod.POST)
    public Result<UserToken> addUserToken(String userId) {
        if (StringUtils.isEmpty(userId))
            return new Result<>(StatusCode._4004);
        return userTokenService.addUserToken(userId);
    }
    
    @RequestMapping(value = "/token/{token}", method = RequestMethod.GET)
    public Result<UserToken> getUserToken(@PathVariable("token") String token) {
        if (StringUtils.isEmpty(token))
            return new Result<>(StatusCode._4004);
        return userTokenService.getByToken(token);
    }
    
    @RequestMapping(value = "/token/refresh", method = RequestMethod.PATCH)
    public Result<?> refreshToken(@RequestParam("token") String token) {
        if (StringUtils.isEmpty(token))
            return new Result<>(StatusCode._4004);
        return userTokenService.refreshToken(token);
    }
    
    @RequestMapping(value ="/combobox",method=RequestMethod.GET)
    public Result<List<Combobox>> getUserList() {
        return userService.getUserList();
    }
    
    @RequestMapping(value = "/my/info", method = RequestMethod.GET)
    public Result<Map<String, Object>> getMyInfo(@RequestParam("userId") String userId) {
        if (StringUtils.isEmpty(userId))
            return new Result<>(StatusCode._4004);
        return userService.getMyInfo(userId);
    }
    
    @RequestMapping(value = "/info/phone", method = RequestMethod.GET)
    public Result<UserInfo> getByPhone(@RequestParam("phone") String phone) {
        if (StringUtils.isEmpty(phone))
            return new Result<>(StatusCode._4004);
        return userService.getByPhone(phone);
    }
}
