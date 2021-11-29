package com.szhengzhu.controller;

import com.szhengzhu.bean.user.WechatInfo;
import com.szhengzhu.bean.user.XwechatInfo;
import com.szhengzhu.service.WechatService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Jehon Zeng
 */
@RestController
@RequestMapping("/wechat")
public class WechatController {

    @Resource
    private WechatService wechatService;

    @PostMapping(value = "/add")
    public void add(@RequestBody WechatInfo wechatInfo) {
        wechatService.add(wechatInfo);
    }

    @PostMapping(value = "/add/x")
    public void addX(@RequestBody XwechatInfo xwechatInfo) {
        wechatService.addX(xwechatInfo);
    }
}
