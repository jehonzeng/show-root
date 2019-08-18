package com.szhengzhu.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.user.UserToken;
import com.szhengzhu.bean.wechat.vo.Cooker;
import com.szhengzhu.bean.wechat.vo.GoodsBase;
import com.szhengzhu.bean.wechat.vo.NavVo;
import com.szhengzhu.client.ShowBaseClient;
import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.client.ShowUserClient;
import com.szhengzhu.core.Result;
import com.szhengzhu.util.StringUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "首页信息：MainController" })
@RestController
@RequestMapping("/main")
public class MainController {

    @Resource
    private ShowBaseClient showBaseClient;

    @Resource
    private ShowUserClient showUserClient;

    @Resource
    private ShowGoodsClient showGoodsClient;

    @ApiOperation(value = "获取首页轮播图等信息", notes = "获取首页轮播图等信息")
    @RequestMapping(value = "/index/nav", method = RequestMethod.GET)
    public Result<List<NavVo>> indexNav() {
        return showBaseClient.listNavAndItem();
    }

    @ApiOperation(value = "首页获取厨师排行榜前三的厨师", notes = "首页获取厨师排行榜前三的厨师")
    @RequestMapping(value = "/index/cooker", method = RequestMethod.GET)
    public Result<List<Cooker>> cookList(HttpServletRequest request) {
        String token = request.getHeader("Show-Token");
        String userId = null;
        if (!StringUtils.isEmpty(token)) {
            Result<UserToken> tokenResult = showUserClient.getUserToken(token);
            if (tokenResult.isSuccess())
                userId = tokenResult.getData().getUserId();
        }
        return showGoodsClient.listCookerRank(userId);
    }

    @ApiOperation(value = "首页获取推荐商品", notes = "首页获取推荐商品")
    @RequestMapping(value = "/index/goods/recommend", method = RequestMethod.GET)
    public Result<List<GoodsBase>> recommend(HttpServletRequest request) {
        String token = request.getHeader("Show-Token");
        String userId = null;
        if (!StringUtils.isEmpty(token)) {
            Result<UserToken> tokenResult = showUserClient.getUserToken(token);
            if (tokenResult.isSuccess())
                userId = tokenResult.getData().getUserId();
        }
        return showGoodsClient.listRecommend(userId);
    }
}
