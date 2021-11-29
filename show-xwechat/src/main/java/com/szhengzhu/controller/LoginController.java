package com.szhengzhu.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailUtil;
import com.szhengzhu.client.ShowBaseClient;
import com.szhengzhu.client.ShowUserClient;
import com.szhengzhu.bean.EncryUser;
import com.szhengzhu.bean.base.ImageInfo;
import com.szhengzhu.bean.user.UserInfo;
import com.szhengzhu.bean.user.UserToken;
import com.szhengzhu.bean.user.XwechatInfo;
import com.szhengzhu.config.FtpConfig;
import com.szhengzhu.config.WechatConfig;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.redis.Redis;
import com.szhengzhu.util.WechatUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import weixin.popular.api.SnsAPI;
import weixin.popular.bean.sns.Jscode2sessionResult;
import weixin.popular.bean.wxa.WxaDUserInfo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Administrator
 */
@Api(tags = {"登录：LoginController"})
@RestController
@RequestMapping("")
public class LoginController {

    @Resource
    private Redis redis;

    @Resource
    private WechatConfig config;

    @Resource
    private FtpConfig ftpServer;

    @Resource
    private ShowBaseClient showBaseClient;

    @Resource
    private ShowUserClient showUserClient;

    @ApiOperation(value = "用户静默授权")
    @RequestMapping(value = "/wechat", method = {RequestMethod.GET, RequestMethod.POST})
    public Result wechat(HttpServletRequest request) {
        String code = request.getParameter("code");
        Jscode2sessionResult sessionResult = SnsAPI.jscode2session(config.getAppId(), config.getSecret(), code);
        ShowAssert.checkTrue(ObjectUtil.isNull(sessionResult) || StrUtil.isNotEmpty(sessionResult.getErrcode()), StatusCode._5004);
        String xopenId = sessionResult.getOpenid();
        Result<UserInfo> userResult = showUserClient.getUserInfoByXopenId(xopenId);
        if (!userResult.isSuccess()) {
            // 小程序中每个请求都是一次会话
            redis.set("session:key:open:" + xopenId, sessionResult.getSession_key(), 6 * 60);
            return new Result<>(StatusCode._4012, xopenId);
        }
        String userId = userResult.getData().getMarkId();
        Result<UserToken> tokenResult = showUserClient.addUserToken(userId);
        return new Result<>(tokenResult.getData().getToken());
    }

    public static void main(String[] args) {
        MailUtil.sendText("771970504@qq.com", "test", "sha", null);
    }

    @ApiOperation(value = "用户授权获取用户信息")
    @RequestMapping(value = "/wechat/auth", method = {RequestMethod.GET, RequestMethod.POST})
    public void wechatInfo(@RequestBody EncryUser encryUser) {
        Object key = redis.get("session:key:open:" + encryUser.getKey());
        ShowAssert.checkNull(key, StatusCode._4037);
        String sessionKey = (String) key;
        WxaDUserInfo wxUser = WechatUtils.decryptUserInfo(sessionKey, encryUser.getEncryptedData(), encryUser.getIv());
        ShowAssert.checkTrue(ObjectUtil.isNull(wxUser), StatusCode._4038);
        wechatInfo(wxUser);
    }

    /**
     * 完善用户信息
     * 1、编辑微信用户信息或新增用户
     * 2、备份用户小程序基本信息
     *
     * @param wxUser
     * @return
     */
    private void wechatInfo(WxaDUserInfo wxUser) {
        UserInfo userInfo = UserInfo.builder().xopenId(wxUser.getOpenId()).unionId(wxUser.getUnionId())
                .nickName(wxUser.getNickName()).gender(Integer.valueOf(wxUser.getGender()))
                .province(wxUser.getProvince()).city(wxUser.getCity()).country(wxUser.getCountry()).language(wxUser.getLanguage())
                .build();
        Result<String> result = showUserClient.addXUser(userInfo);
        new Thread(() -> {
            ImageInfo imageInfo = WechatUtils.downLoadImage(ftpServer, wxUser.getAvatarUrl());
            if (ObjectUtil.isNotNull(imageInfo) && result.isSuccess()) {
                showBaseClient.addImgInfo(imageInfo);
                UserInfo user = UserInfo.builder().markId(result.getData()).headerImg(imageInfo.getMarkId()).build();
                showUserClient.modifyUser(user);
            }
            XwechatInfo wInfo = XwechatInfo.builder().openId(wxUser.getOpenId())
                    .headerImg(wxUser.getAvatarUrl()).createTime(DateUtil.date()).build();
            showUserClient.addWechat(wInfo);
        }).start();
    }
}
