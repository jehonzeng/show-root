package com.szhengzhu.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.user.UserInfo;
import com.szhengzhu.client.ShowUserClient;
import com.szhengzhu.common.Commons;
import com.szhengzhu.core.LoginBase;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.util.ShowUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = { "用户登录操作：LoginController" })
@RestController
@RequestMapping("")
public class LoginController {

    @Resource
    private ShowUserClient userClient;

    @ApiOperation(value = "通过手机号获取短信", notes = "用户通过手机号获取短信")
    @RequestMapping(value = "/code", method = RequestMethod.GET)
    public Result<?> getCode(HttpSession session,
            @ApiParam(name = "phone", value = "手机号", required = true) @RequestParam("phone") String phone) {
        if (!ShowUtils.isPhone(phone))
            return new Result<String>(StatusCode._4000);
        LoginBase login = (LoginBase) session.getAttribute("login_base");
        login = login == null ? new LoginBase(phone, 6) : login;
        login.refresh(phone);
//        if (login.isOften(5))
//            return new Result<String>(StatusCode._4001);
        Result<UserInfo> result = userClient.getManager(phone);
        if (!result.isSuccess())
            return new Result<>(StatusCode._4011);
        login.setMarkId(result.getData().getMarkId());
        session.setAttribute("login_base", login);
        System.out.println("===================================phone:" + phone + " code:"
                + login.getCode() + "========================================");
//        Result<String> sendResult = SmsUtils.send(result.getData().getPhone(), login.getCode());
        Result<String> sendResult = new Result<>();
        System.out.println(login.getPhone() + ": 验证码为" + login.getCode());
        sendResult.setData(login.getCode());
        return sendResult;
    }

    @ApiOperation(value = "用户登录", notes = "用户登录后台系统")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public Result<?> login(HttpSession session,
            @ApiParam(name = "phone", value = "手机号", required = true) @RequestParam("phone") String phone,
            @ApiParam(name = "code", value = "验证码", required = true) @RequestParam("code") String code) {
        LoginBase loginBase = (LoginBase) session.getAttribute("login_base");
        if (loginBase == null)
            return new Result<String>(StatusCode._4002);
        if (loginBase.isOver())
            return new Result<String>(StatusCode._4003);
        if (!loginBase.equals(phone, code))
            return new Result<String>(StatusCode._4002);
        session.setAttribute(Commons.SESSION, loginBase.getMarkId());
        session.removeAttribute("login_base");
        return new Result<>();
    }
    
    @ApiOperation(value = "后台主动退出登录", notes = "后台主动退出登录")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Result<?> logOut(HttpSession session) {
        session.removeAttribute(Commons.SESSION);
        return new Result<>();
    }

}
