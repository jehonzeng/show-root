package com.szhengzhu.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.application.WechatConfig;
import com.szhengzhu.bean.base.ImageInfo;
import com.szhengzhu.bean.user.UserInfo;
import com.szhengzhu.bean.user.UserToken;
import com.szhengzhu.client.ShowBaseClient;
import com.szhengzhu.client.ShowUserClient;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.util.SmsUtils;
import com.szhengzhu.util.StringUtils;
import com.szhengzhu.util.WechatUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import weixin.popular.api.SnsAPI;
import weixin.popular.bean.sns.SnsToken;
import weixin.popular.bean.user.User;

@Api(tags = {"登录模块：LoginController"})
@RestController
public class LoginController {
    
    @Resource 
    private ShowUserClient showUserClient;
    
    @Resource
    private WechatConfig wechatConfig;
    
    @Resource
    private ShowBaseClient showBaseClient;

    @ApiOperation(value = "用户静默授权", notes = "用户静默授权")
    @RequestMapping(value = "/wechat", method = RequestMethod.GET)
    public Result<?> wechat(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String code = request.getParameter("code");
        SnsToken token = SnsAPI.oauth2AccessToken(wechatConfig.getAppid(), wechatConfig.getSecret(), code);
        if (token != null && token.isSuccess()) {
            String openId = token.getOpenid();
            Result<UserInfo> userResult = showUserClient.getUserByOpenId(openId);
            if (userResult.getCode().equals("200") && userResult.getData() == null) {
                return new Result<>(StatusCode._4012);
            } else if (userResult.isSuccess()) {
                Map<String, String> resultMap = new HashMap<>();
                UserInfo user = userResult.getData();
                Result<UserToken> tokenResult = showUserClient.addUserToken(user.getMarkId());
                resultMap.put("token", tokenResult.getData().getToken());
                return new Result<>(resultMap);
            } else {
                return new Result<>(StatusCode._5000);
            }
        }
        return new Result<>(StatusCode._5004); 
    }
    
    @ApiOperation(value = "用户非静默授权", notes = "用户非静默授权")
    @RequestMapping(value = "/oauthWechat", method = RequestMethod.GET)
    public Result<?> oauthWechat(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String code = request.getParameter("code");
        SnsToken token = SnsAPI.oauth2AccessToken(wechatConfig.getAppid(), wechatConfig.getSecret(), code);
        if (token != null && token.isSuccess()) {
            String openId = token.getOpenid();
            String accessToken = token.getAccess_token();
            String refreshToken = token.getRefresh_token();
            User wxUser = WechatUtils.getWxUser(wechatConfig.getAppid(), openId, accessToken, refreshToken);
            UserInfo userInfo = getUserInfo(openId, wxUser);
            if (userInfo == null)
                return new Result<>(StatusCode._5000);
            Result<UserToken> tokenResult = showUserClient.addUserToken(userInfo.getMarkId());
            if (tokenResult.isSuccess()) {
                Map<String, String> resultMap = new HashMap<>();
                resultMap.put("token", tokenResult.getData().getToken());
                return new Result<>(resultMap);
            } else {
                return new Result<>(StatusCode._5000);  //5000错误一律为feign请求发生错误统称为服务器异常
            }
            
        }
        return new Result<>(StatusCode._5004); 
    }   
    
    private UserInfo getUserInfo(String openId, User wxUser) {
        UserInfo userInfo = null;
        if (wxUser.isSuccess()) {
            Result<UserInfo> userResult = showUserClient.getUserByOpenId(openId);
            if (userResult.getCode().equals("200") && userResult.getData() == null) {
                ImageInfo imageInfo = WechatUtils.downLoadImage(wxUser.getHeadimgurl());
                showBaseClient.addImgInfo(imageInfo);
                userInfo = new UserInfo();
                userInfo.setNickName(wxUser.getNickname());
                userInfo.setHeaderImg(imageInfo.getMarkId());
                userInfo.setGender(wxUser.getSex());
                userInfo.setCity(wxUser.getCity());
                userInfo.setProvince(wxUser.getProvince());
                userInfo.setCountry(wxUser.getCountry());
                userInfo.setLanguage(wxUser.getLanguage());
                userInfo.setUserLevel("");
                userInfo.setWopenId(openId);
                userInfo.setUnionId(wxUser.getUnionid());
                userInfo.setWechatStatus(wxUser.getSubscribe());
                userInfo = showUserClient.addUser(userInfo).getData();
            } else if (userResult.getCode().equals("200") && userResult.getData() != null) {
                userInfo = userResult.getData();
            }
        }
        return userInfo;
    }
    
    @ApiOperation(value = "用户手机发送验证码", notes = "用户手机发送验证码")
    @RequestMapping(value = "/code", method = RequestMethod.GET)
    public Result<String> getCode(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String phone = request.getParameter("phone");
        if (StringUtils.isEmpty(phone))
            return new Result<>(StatusCode._4004);
        int time = (int) session.getAttribute("time");
        if (time > 6) 
            return new Result<>(StatusCode._4001);
        session.setAttribute("time", time);
        String code = (String) session.getAttribute("smsCode");
        if (code == null || code.length() != 6) {
            code = (int)((Math.random() * 9 + 1) * 100000) + "";
        }
        Result<String> result = SmsUtils.send(phone, code);
        if (result.getCode().equals("200")) {
            session.setAttribute("userPhone", phone);
            session.setAttribute("smsCode", code);
            session.setAttribute("send_time", System.currentTimeMillis());
            session.setMaxInactiveInterval(60);
            return new Result<>();
        }
        return result;
    }
    
    public Result<?> login(HttpServletRequest request, HttpSession session) {
        String phone = request.getParameter("phone");
        String code = request.getParameter("code");
        String userPhone = (String) session.getAttribute("userPhone");
        String smsCode = (String) session.getAttribute("smsCode");
        if ((phone.equals(userPhone) && code.equals(smsCode))) {
            Long long_time = (Long) session.getAttribute("long_time");
            boolean time_out = System.currentTimeMillis() - long_time.longValue() > 600000;
            if (time_out)
                return new Result<>(StatusCode._4003);
        } else {
            return new Result<>(StatusCode._4002);
        }
        Result<UserInfo> userResult = showUserClient.getUserByPhone(phone);
        if (userResult.isSuccess()) {
            Result<UserToken> tokenResult = showUserClient.addUserToken(userResult.getData().getMarkId());
            if (tokenResult.isSuccess()) {
                Map<String, String> resultMap = new HashMap<>();
                resultMap.put("token", tokenResult.getData().getToken());
                return new Result<>(resultMap);
            } 
        }
        return new Result<>(StatusCode._5000);  //5000错误一律为feign请求发生错误统称为服务器异常
    }
    
}
