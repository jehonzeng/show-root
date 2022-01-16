package com.szhengzhu.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.szhengzhu.feign.ShowBaseClient;
import com.szhengzhu.feign.ShowUserClient;
import com.szhengzhu.bean.base.ImageInfo;
import com.szhengzhu.bean.user.UserInfo;
import com.szhengzhu.bean.user.UserToken;
import com.szhengzhu.config.FtpServer;
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
import org.springframework.web.bind.annotation.RestController;
import weixin.popular.api.SnsAPI;
import weixin.popular.bean.sns.SnsToken;
import weixin.popular.bean.user.User;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jehon Zeng
 */
@Slf4j
@Api(tags = {"登录专题：LoginController"})
@RestController
public class LoginController {

    @Resource
    private Redis redis;

    @Resource
    private ShowUserClient showUserClient;

    @Resource
    private WechatConfig wechatConfig;

    @Resource
    private ShowBaseClient showBaseClient;

    @Resource
    private FtpServer ftpServer;

    private static final String CODE_CACHE_PRE = "wechat:code:send:";

    @ApiOperation(value = "用户静默授权", notes = "用户静默授权")
    @GetMapping(value = "/wechat")
    public Result wechat(HttpServletRequest request) {
        String code = request.getParameter("code");
        SnsToken token = SnsAPI.oauth2AccessToken(wechatConfig.getAppId(), wechatConfig.getSecret(), code);
        ShowAssert.checkTrue((token == null || !token.isSuccess()), StatusCode._5004);
        String openId = token.getOpenid();
        Result<UserInfo> userResult = showUserClient.getUserByOpenId(openId);
        ShowAssert.checkTrue(!userResult.isSuccess(), StatusCode._4012);
        Map<String, String> resultMap = new HashMap<>(2);
        UserInfo user = userResult.getData();
        Result<UserToken> tokenResult = showUserClient.addUserToken(user.getMarkId());
        resultMap.put("token", tokenResult.getData().getToken());
        return new Result<>(resultMap);
    }

    @ApiOperation(value = "用户非静默授权", notes = "用户非静默授权")
    @GetMapping(value = "/oauthWechat")
    public Result oauthWechat(HttpServletRequest request) {
        String code = request.getParameter("code");
        SnsToken token = SnsAPI.oauth2AccessToken(wechatConfig.getAppId(), wechatConfig.getSecret(), code);
        ShowAssert.checkTrue((token == null || !token.isSuccess()), StatusCode._5004);
        String openId = token.getOpenid();
        String accessToken = token.getAccess_token();
        String refreshToken = token.getRefresh_token();
        User wxUser = WechatUtils.getWxUser(wechatConfig.getAppId(), openId, accessToken, refreshToken);
        UserInfo userInfo = null;
        if (wxUser.isSuccess()) {
            userInfo = getUserInfo(openId, wxUser);
        }
        ShowAssert.checkNull(userInfo, StatusCode._5000);
        Result<UserToken> tokenResult = showUserClient.addUserToken(userInfo.getMarkId());
        // 5000错误一律为feign请求发生错误统称为服务器异常
        ShowAssert.checkTrue(!tokenResult.isSuccess(), StatusCode._5000);
        Map<String, String> resultMap = new HashMap<>(4);
        resultMap.put("token", tokenResult.getData().getToken());
        return new Result<>(resultMap);
    }

    /**
     * 获取用户信息
     *
     * @param openId
     * @param wxUser
     * @return
     */
    private UserInfo getUserInfo(String openId, User wxUser) {
        Result<UserInfo> userResult = showUserClient.getUserByOpenId(openId);
        if (userResult.isSuccess()) {
            return userResult.getData();
        }
        if (!Contacts.SUCCESS_CODE.equals(userResult.getCode())) {
            return null;
        }
        String headerImg = null;
        ImageInfo imageInfo = WechatUtils.downLoadImage(ftpServer, wxUser.getHeadimgurl());
        if (ObjectUtil.isNotNull(imageInfo)) {
            showBaseClient.addImgInfo(imageInfo);
            headerImg = imageInfo.getMarkId();
        }
        return createUser(wxUser, headerImg, openId);
    }

    /**
     * 创建新用户
     *
     * @param wxUser
     * @param headerImg
     * @param openId
     * @return
     */
    private UserInfo createUser(User wxUser, String headerImg, String openId) {
        UserInfo userInfo = UserInfo.builder().nickName(wxUser.getNickname()).headerImg(headerImg)
                .gender(wxUser.getSex()).city(wxUser.getCity()).province(wxUser.getProvince()).country(wxUser.getCountry())
                .language(wxUser.getLanguage()).userLevel("").wopenId(openId).unionId(wxUser.getUnionid()).wechatStatus(wxUser.getSubscribe())
                .createTime(DateUtil.date()).build();
        return showUserClient.addUser(userInfo).getData();
    }

    @ApiOperation(value = "用户手机发送验证码", notes = "用户手机发送验证码")
    @GetMapping(value = "/code")
    public void getCode(HttpServletRequest request) {
        String phone = request.getParameter("phone");
        ShowAssert.checkTrue(!Validator.isMobile(phone), StatusCode._4000);
        int sendTimes = 6;
        String codeCacheKey = CODE_CACHE_PRE + phone;
        Object obj = redis.get(codeCacheKey);
        LoginBase loginBase = ObjectUtil.isNull(obj) ? new LoginBase(phone) : JSON.parseObject(JSON.toJSONString(obj), LoginBase.class);
        ShowAssert.checkTrue(loginBase.isOften(sendTimes), StatusCode._4001);
        loginBase.refresh(phone);
        redis.set(codeCacheKey, loginBase, 3 * 60);
        SmsUtils.send(phone, loginBase.getCode());
        log.info("{}的验证码：{}", phone, loginBase.getCode());
    }

    @ApiOperation(value = "用户网页登录商城", notes = "用户网页登录商城")
    @GetMapping(value = "/web/login")
    public Result login(HttpServletRequest request) {
        String phone = request.getParameter("phone");
        String code = request.getParameter("code");
        String codeCacheKey = CODE_CACHE_PRE + phone;
        Object obj = redis.get(codeCacheKey);
        ShowAssert.checkNull(obj, StatusCode._4003);
        LoginBase loginBase = JSON.parseObject(JSON.toJSONString(obj), LoginBase.class);
        ShowAssert.checkTrue(!loginBase.equals(phone, code), StatusCode._4002);
        redis.del(codeCacheKey);
        Result<UserInfo> userResult = showUserClient.getUserByPhone(phone);
        // 5000错误一律为feign请求发生错误统称为服务器异常
        ShowAssert.checkTrue(!userResult.isSuccess(), StatusCode._5000);
        Result<UserToken> tokenResult = showUserClient.addUserToken(userResult.getData().getMarkId());
        ShowAssert.checkTrue(!tokenResult.isSuccess(), StatusCode._5000);
        Map<String, String> resultMap = new HashMap<>(4);
        resultMap.put("token", tokenResult.getData().getToken());
        return new Result<>(resultMap);
    }
}
