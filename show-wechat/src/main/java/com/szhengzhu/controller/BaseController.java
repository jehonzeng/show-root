package com.szhengzhu.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.application.WechatConfig;
import com.szhengzhu.bean.base.AreaInfo;
import com.szhengzhu.bean.user.UserToken;
import com.szhengzhu.bean.vo.DeliveryDate;
import com.szhengzhu.client.ShowBaseClient;
import com.szhengzhu.client.ShowOrderClient;
import com.szhengzhu.client.ShowUserClient;
import com.szhengzhu.core.Result;
import com.szhengzhu.util.WechatUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"基础信息：BaseController"})
@RestController
@RequestMapping("/base")
public class BaseController {
    
    @Resource
    private WechatConfig wechatConfig;
    
    @Resource
    private ShowOrderClient showOrderClient;
    
    @Resource
    private ShowBaseClient showBaseClient;
    
    @Resource
    private ShowUserClient showUserClient;

    @ApiOperation(value = "获取配置签名信息", notes = "获取配置签名信息")
    @RequestMapping(value  = "/config", method = RequestMethod.GET)
    public Result<?> getConfig(HttpServletRequest request) {
        String url = request.getParameter("url");
        return new Result<>(WechatUtils.buildConfigInfo(wechatConfig, url));
    }
    
    @ApiOperation(value = "获取最近八天配送日期", notes = "获取最近八天配送日期")
    @RequestMapping(value = "/deliverydate/list", method = RequestMethod.GET)
    public Result<List<DeliveryDate>> listDeliveryDate() {
        return showOrderClient.listDeliveryDate();
    }
    
    @ApiOperation(value = "获取地区下拉列表", notes = "获取地区下拉列表")
    @RequestMapping(value = "/area/list", method = RequestMethod.GET)
    public Result<List<AreaInfo>> listArea() {
        return showBaseClient.listAllArea();
    }
    
    @RequestMapping(value = "/token", method = RequestMethod.GET)
    public Result<String> getToken() {
        Result<UserToken> tokenResult = showUserClient.addUserToken("60759249534189568");
        return new Result<>(tokenResult.getData().getToken());
    }
}
