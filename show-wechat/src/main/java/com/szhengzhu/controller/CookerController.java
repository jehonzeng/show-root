package com.szhengzhu.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.user.UserToken;
import com.szhengzhu.bean.wechat.vo.Cooker;
import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.client.ShowUserClient;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "厨师：CookerController")
@RestController
@RequestMapping("/cookers")
public class CookerController {

    @Resource
    private ShowGoodsClient showGoodsClient;

    @Resource
    private ShowUserClient showUserClient;

    @ApiOperation(value = "获取厨师菜品销量排名列表", notes = "获取厨师菜品销量排名列表")
    @RequestMapping(value = "/sale/rank/goods/page", method = RequestMethod.POST)
    public Result<PageGrid<Cooker>> listCooker(HttpServletRequest request, @RequestBody PageParam<String> cookerPage) {
        String token = request.getHeader("Show-Token");
        String userId = null;
        if (token != null) {
            Result<UserToken> tokenResult = showUserClient.getUserToken(token);
            if (tokenResult.isSuccess())
                userId = tokenResult.getData().getUserId();
        }
        cookerPage.setData(userId);
        return showGoodsClient.pageCookerRank(cookerPage);
    }
    
    @ApiOperation(value = "获取厨师详情", notes = "获取厨师详情")
    @RequestMapping(value = "/{cookerId}", method = RequestMethod.GET)
    public Result<?> getCookerDetail(HttpServletRequest request, @PathVariable("cookerId") String cookerId) {
        String token = request.getHeader("Show-Token");
        String userId = null;
        if (token != null) {
            Result<UserToken> tokenResult = showUserClient.getUserToken(token);
            if (tokenResult.isSuccess())
                userId = tokenResult.getData().getUserId();
        }
        return showGoodsClient.getCookerDetail(cookerId, userId);
    }
}
