package com.szhengzhu.controller;

import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.client.ShowUserClient;
import com.szhengzhu.bean.user.UserToken;
import com.szhengzhu.bean.wechat.vo.Cooker;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

/**
 * @author Jehon Zeng
 */
@Validated
@Api(tags = "厨师专题：CookerController")
@RestController
@RequestMapping("/cookers")
public class CookerController {

    @Resource
    private ShowGoodsClient showGoodsClient;

    @Resource
    private ShowUserClient showUserClient;

    @ApiOperation(value = "获取厨师菜品销量排名列表", notes = "获取厨师菜品销量排名列表")
    @PostMapping(value = "/sale/rank/goods/page")
    public Result<PageGrid<Cooker>> listCooker(HttpServletRequest request, @RequestBody PageParam<String> cookerPage) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        cookerPage.setData(userToken.getUserId());
        return showGoodsClient.pageCookerRank(cookerPage);
    }

    @ApiOperation(value = "获取厨师详情", notes = "获取厨师详情")
    @GetMapping(value = "/{cookerId}")
    public Result<Cooker> getCookerDetail(HttpServletRequest request, @PathVariable("cookerId") @NotBlank String cookerId) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        return showGoodsClient.getCookerDetail(cookerId, userToken.getUserId());
    }
}
