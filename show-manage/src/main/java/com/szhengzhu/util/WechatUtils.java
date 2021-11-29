package com.szhengzhu.util;

import com.szhengzhu.bean.WechatButton;
import com.szhengzhu.config.WechatConfig;
import weixin.popular.api.MediaAPI;
import weixin.popular.api.MessageAPI;
import weixin.popular.bean.BaseResult;
import weixin.popular.bean.media.Media;
import weixin.popular.bean.media.MediaType;
import weixin.popular.bean.menu.Button;
import weixin.popular.bean.menu.MenuButtons;
import weixin.popular.bean.message.message.Message;
import weixin.popular.bean.message.message.TextMessage;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class WechatUtils {
    
    public static MenuButtons createMenu(List<WechatButton> buttonList) {
        MenuButtons menu = new MenuButtons();
        Button[] buttons = new Button[buttonList.size()];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = createButton(buttonList.get(i));
        }
        menu.setButton(buttons);
        return menu;
    }
    
    private static Button createButton(WechatButton model) {
        Button b = new Button();
        b.setName(model.getName());
        if (model.getType().equals(0)) {  // 网页类型
            b.setType("view");
            b.setUrl(model.getMemo());
        } else if (model.getType().equals(1)) { // 点击类型
            b.setType("click");
            b.setKey(model.getMemo());
        } else if (model.getType().equals(3)) { // 小程序类型
            b.setType("miniprogram");
            b.setPagepath(model.getMemo());
            b.setAppid("wx8ebc5fb8e589f99c"); // 小程序appid
            b.setUrl("https://m.lujishou.com/home"); // 不支持小程序的老版本客户端将打开本url
        } else if (model.getType().equals(2) && model.getList() != null
                && model.getList().size() > 0) {
            b.setType("view");
            b.setSub_button(new ArrayList<Button>());
            for (int i = 0; i < model.getList().size(); i++) {
                b.getSub_button().add(createButton(model.getList().get(i)));
            }
        }
        return b;
    }
    
    /**
     * 向用户发送验证码到公众号
     * 
     * @date 2019年8月23日 下午3:50:10
     * @param config
     * @param openId
     * @param smsMsg
     */
    public static BaseResult sendSmg(WechatConfig config, String openId, String smsMsg) {
        TextMessage message = new TextMessage(openId, smsMsg);
        BaseResult result = MessageAPI.messageCustomSend(config.refreshToken(), message);
        return result;
    }

    /**
     * 根据url上传图片素材
     * 
     * @param url
     * @return
     */
    public static Media uplodMedia(WechatConfig config, String url) {
        Media media = null;
        try {
            media = MediaAPI.mediaUpload(config.getToken(), MediaType.image, new URI(url));
            if (!media.isSuccess() && media.getErrcode().equals("40001")) {
                media = MediaAPI.mediaUpload(config.refreshToken(), MediaType.image, new URI(url));
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return media;
    }

    public static void messageSend(WechatConfig config, Message message) {
        BaseResult result = MessageAPI.messageCustomSend(config.getToken(), message);
        if (result.getErrcode().equals("40001")) {
            MessageAPI.messageCustomSend(config.refreshToken(), message);
        }
    }
}
