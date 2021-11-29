package com.szhengzhu.controller;

import com.szhengzhu.client.ShowActivityClient;
import com.szhengzhu.client.ShowUserClient;
import com.szhengzhu.bean.activity.SceneGoods;
import com.szhengzhu.bean.activity.SceneItem;
import com.szhengzhu.bean.user.UserToken;
import com.szhengzhu.core.Result;
import com.szhengzhu.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Validated
@Api(tags = {"现场销售：SceneController"})
@RestController
@RequestMapping("/v1/scene")
public class SceneController {

    @Resource
    private ShowActivityClient showActivityClient;

    @Resource
    private ShowUserClient showUserClient;

    @ApiOperation(value = "获取商品列表", notes = "获取商品列表")
    @GetMapping(value = "/goods/list")
    public Result<List<SceneGoods>> listForeGoods() {
        return showActivityClient.listSceneGoods();
    }

    @ApiOperation(value = "创建订单", notes = "创建订单")
    @PostMapping(value = "/order/create")
    public Result<Map<String, Object>> createOrder(HttpServletRequest request, @RequestBody @NotEmpty List<String> goodsIdList) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        return showActivityClient.createSceneOrder(goodsIdList, userToken.getUserId());
    }

    @ApiOperation(value = "获取未领取的商品列表", notes = "获取未领取的商品列表")
    @GetMapping(value = "/order/item/unreceive")
    public Result<List<SceneItem>> listUnReceiveGoods(HttpServletRequest request) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        return showActivityClient.listSceneUnReceiveGoods(userToken.getUserId());
    }

    @ApiOperation(value = "获取已领取的商品列表")
    @GetMapping(value = "/order/item/receive")
    public Result<List<SceneItem>> listReceiveGoods(HttpServletRequest request) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        return showActivityClient.listSceneReceiveGoods(userToken.getUserId());
    }

    @ApiOperation(value = "用户领取商品", notes = "用户领取商品")
    @PatchMapping(value = "/order/item/receive")
    public Result<List<String>> receiveGoods(HttpServletRequest request, @RequestBody @NotEmpty List<String> idsList) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        return showActivityClient.receiveGoods(idsList, userToken.getUserId());

    }
}
