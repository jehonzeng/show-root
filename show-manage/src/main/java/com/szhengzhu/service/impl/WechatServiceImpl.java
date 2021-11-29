package com.szhengzhu.service.impl;

import com.alibaba.fastjson.JSON;
import com.szhengzhu.bean.WechatButton;
import com.szhengzhu.config.WechatConfig;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.service.WechatService;
import com.szhengzhu.util.WechatUtils;
import org.springframework.stereotype.Service;
import weixin.popular.api.MenuAPI;
import weixin.popular.bean.BaseResult;
import weixin.popular.bean.menu.MenuButtons;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jehon Zeng
 */
@Service("wechatService")
public class WechatServiceImpl implements WechatService {

    @Resource
    private WechatConfig wechatConfig;

    @Override
    public Result refreshMenu(String menuJson) {
        String errorCode = "40001";
        try {
            List<WechatButton> buttons = JSON.parseArray(menuJson, WechatButton.class);
            MenuButtons menu = WechatUtils.createMenu(buttons);
            BaseResult baseResult = MenuAPI.menuCreate(wechatConfig.getToken(), menu);
            if (!baseResult.isSuccess() && errorCode.equals(baseResult.getErrcode())) {
                baseResult = MenuAPI.menuCreate(wechatConfig.refreshToken(), menu);
            } else {
                return new Result<>(baseResult.getErrcode(), baseResult.getErrmsg());
            }
            return new Result<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(StatusCode._4004);
        }
    }

}
