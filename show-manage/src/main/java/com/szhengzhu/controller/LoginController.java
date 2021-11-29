package com.szhengzhu.controller;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.szhengzhu.annotation.SystemLog;
import com.szhengzhu.client.ShowUserClient;
import com.szhengzhu.bean.user.UserInfo;
import com.szhengzhu.bean.user.UserToken;
import com.szhengzhu.config.WechatConfig;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.LoginBase;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.redis.Redis;
import com.szhengzhu.util.SmsUtils;
import com.szhengzhu.util.WechatUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import weixin.popular.api.SnsAPI;
import weixin.popular.bean.BaseResult;
import weixin.popular.bean.sns.SnsToken;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jehon Zeng
 */
@Slf4j
@Api(tags = {"用户登录操作：LoginController"})
@RestController
@RequestMapping("")
public class LoginController {

    @Resource
    private Redis redis;

    @Resource
    private ShowUserClient showUserClient;

    @Resource
    private WechatConfig config;

    @ApiOperation(value = "通过手机号获取短信", notes = "用户通过手机号获取短信")
    @GetMapping(value = "/code")
    public void getCode(HttpSession session, HttpServletRequest request) {
        String phone = request.getParameter("phone");
//        Validator.isMatchRegex("^(1)[\\d]{10}$", phone))
        ShowAssert.checkTrue(!Validator.isMobile(phone), StatusCode._4000);
        int sendTimes = 5;
        LoginBase login = (LoginBase) session.getAttribute("login_base");
        login = ObjectUtil.isNull(login) ? new LoginBase(phone) : login;
        login.refresh(phone);
        ShowAssert.checkTrue(login.isOften(sendTimes), StatusCode._4001);
        Result<UserInfo> result = showUserClient.getManager(phone);
        ShowAssert.checkTrue(!result.isSuccess(), StatusCode._4017);
        login.setMarkId(result.getData().getMarkId());
        session.setAttribute("login_base", login);

//        String codeCacheKey = "manage:code:send:" + phone;
//        Object obj = redis.get(codeCacheKey);
//        LoginBase login = ObjectUtil.isNull(obj) ? new LoginBase(phone) : JSON.parseObject(JSON.toJSONString(obj), LoginBase.class);
//        login.refresh(phone);
//        ShowAssert.checkTrue(login.isOften(sendTimes), StatusCode._4001);
//        Result<UserInfo> result = showUserClient.getManager(phone);
//        ShowAssert.checkTrue(!result.isSuccess(), StatusCode._4017);
//        login.setMarkId(result.getData().getMarkId());
//        redis.set(codeCacheKey, login, 3 * 60);
        BaseResult wechatResult = WechatUtils.sendSmg(config, result.getData().getWopenId(), login.getCode());
        log.info(wechatResult.toString());
        if (!wechatResult.isSuccess()) {
            SmsUtils.send(result.getData().getPhone(), login.getCode());
        }
        log.info("{}: 验证码为 {}", login.getPhone(), login.getCode());
    }

    @SystemLog(desc = "登录系统")
    @ApiOperation(value = "用户登录", notes = "用户登录后台系统")
    @GetMapping(value = "/login")
    public void login(HttpSession session, HttpServletRequest request) {
        String phone = request.getParameter("phone");
        String code = request.getParameter("code");
//        String codeCacheKey = "manage:code:send:" + phone;
//        Object obj = redis.get(codeCacheKey);
//        ShowAssert.checkNull(obj, StatusCode._4003);
//        LoginBase loginBase = JSON.parseObject(JSON.toJSONString(obj), LoginBase.class);
//        ShowAssert.checkTrue(!loginBase.equals(phone, code), StatusCode._4002);
//        redis.del(codeCacheKey);
        LoginBase loginBase = (LoginBase) session.getAttribute("login_base");
        ShowAssert.checkTrue(ObjectUtil.isNull(loginBase) || StrUtil.isEmpty(phone) || StrUtil.isEmpty(code)
                || !loginBase.equals(phone, code), StatusCode._4002);
        ShowAssert.checkTrue(loginBase.isOver(), StatusCode._4003);
        session.setAttribute(Contacts.LJS_SESSION, loginBase.getMarkId());
        session.removeAttribute("login_base");
    }

    @SystemLog(desc = "安全退出系统")
    @ApiOperation(value = "后台主动退出登录", notes = "后台主动退出登录")
    @GetMapping(value = "/logout")
    public Result logOut(HttpSession session) {
        session.removeAttribute(Contacts.LJS_SESSION);
        return new Result<>();
    }

    @ApiOperation(value = "获取管理员姓名与手机号", notes = "获取管理员姓名与手机号")
    @GetMapping(value = "/manager/list")
    public Result<List<Map<String, String>>> listManager() {
        return showUserClient.listManager();
    }

    @ApiOperation(value = "内部人员微信登录", notes = "内部人员微信登录")
    @GetMapping(value = "/wechat")
    public Result wechat(HttpServletRequest request, HttpSession session) {
        String code = request.getParameter("code");
        SnsToken token = SnsAPI.oauth2AccessToken(config.getAppId(), config.getSecret(), code);
        ShowAssert.checkTrue((ObjectUtil.isNull(token) || !token.isSuccess()), StatusCode._5004);
        String openId = token.getOpenid();
        Result<UserInfo> userResult = showUserClient.getUserByOpenId(openId);
        ShowAssert.checkTrue(Contacts.SUCCESS_CODE.equals(userResult.getCode()) && userResult.getData() == null, StatusCode._4012);
        ShowAssert.checkTrue(!userResult.isSuccess(), StatusCode._5000);
        UserInfo user = userResult.getData();
        // 验证内部人员
        Result<Boolean> result = showUserClient.checkManage(user.getMarkId());
        ShowAssert.checkResult(result);
        ShowAssert.checkTrue(Boolean.FALSE.equals(result.getData()), StatusCode._5015);
        // 后台微信登录用到session
        session.setAttribute(Contacts.LJS_SESSION, user.getMarkId());
        // 微信端采购返回token
        Result<UserToken> tokenResult = showUserClient.addUserToken(user.getMarkId());
        Map<String, String> resultMap = new HashMap<>(1);
        resultMap.put("token", tokenResult.getData().getToken());
        return new Result<>(resultMap);
    }
}
