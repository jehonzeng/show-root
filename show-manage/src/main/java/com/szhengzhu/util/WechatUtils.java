package com.szhengzhu.util;

import java.util.ArrayList;
import java.util.List;

import com.szhengzhu.bean.WechatButton;

import weixin.popular.bean.menu.Button;
import weixin.popular.bean.menu.MenuButtons;

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
        if (model.getType().equals(0)) {
            b.setType("view");
            b.setUrl(model.getMemo());
        } else if (model.getType().equals(1)) {
            b.setType("click");
            b.setKey(model.getMemo());
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
}
