package com.szhengzhu.controller;

import cn.hutool.core.util.ObjectUtil;
import com.szhengzhu.feign.ShowBaseClient;
import com.szhengzhu.feign.ShowGoodsClient;
import com.szhengzhu.feign.ShowUserClient;
import com.szhengzhu.bean.user.UserToken;
import com.szhengzhu.bean.wechat.vo.Cooker;
import com.szhengzhu.bean.wechat.vo.GoodsBase;
import com.szhengzhu.bean.wechat.vo.NavBase;
import com.szhengzhu.core.Result;
import com.szhengzhu.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Jehon Zeng
 */
@Api(tags = {"首页专题：MainController"})
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
    @GetMapping(value = "/index/nav")
    public Result<List<NavBase>> indexNav() {
        return showBaseClient.listNavAndItem();
    }

    @ApiOperation(value = "首页获取厨师排行榜前三的厨师", notes = "首页获取厨师排行榜前三的厨师")
    @GetMapping(value = "/index/cooker")
    public Result<List<Cooker>> cookList(HttpServletRequest request) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        String userId = null;
        if (ObjectUtil.isNotNull(userToken)) {
            userId = userToken.getUserId();
        }
        return showGoodsClient.listCookerRank(userId);
    }

    @ApiOperation(value = "首页获取推荐商品", notes = "首页获取推荐商品")
    @GetMapping(value = "/index/goods/recommend")
    public Result<List<GoodsBase>> recommend(HttpServletRequest request) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        String userId = null;
        if (ObjectUtil.isNotNull(userToken)) {
            userId = userToken.getUserId();
        }
        return showGoodsClient.listRecommend(userId);
    }
}
