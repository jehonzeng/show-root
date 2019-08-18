package com.szhengzhu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.szhengzhu.application.WechatConfig;
import com.szhengzhu.bean.WechatButton;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.service.WechatService;
import com.szhengzhu.util.WechatUtils;

import weixin.popular.api.MenuAPI;
import weixin.popular.bean.BaseResult;
import weixin.popular.bean.menu.MenuButtons;

@Service("wechatService")
public class WechatServiceImpl implements WechatService {

    @Resource
    private WechatConfig wechatConfig;
    
    @Override
    public Result<?> refreshMenu(String menuJson) {
        try {
            List<WechatButton> buttons = JSON.parseArray(menuJson, WechatButton.class);
            MenuButtons menu = WechatUtils.createMenu(buttons);
            BaseResult baseResult = MenuAPI.menuCreate(wechatConfig.getToken(), menu);
            if (!baseResult.isSuccess() && baseResult.getErrcode().equals("40001")) 
                baseResult = MenuAPI.menuCreate(wechatConfig.refreshToken(), menu);
            if (!baseResult.isSuccess())
                return new Result<>(baseResult.getErrcode(), baseResult.getErrmsg());
            return new Result<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(StatusCode._4004);
        }
    }

}
