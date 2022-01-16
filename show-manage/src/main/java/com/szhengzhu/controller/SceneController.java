package com.szhengzhu.controller;

import com.szhengzhu.feign.ShowActivityClient;
import com.szhengzhu.bean.activity.SceneGoods;
import com.szhengzhu.bean.activity.SceneInfo;
import com.szhengzhu.bean.activity.SceneOrder;
import com.szhengzhu.code.OrderStatus;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.rabbitmq.Sender;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * @author Administrator
 */
@Validated
@Api(tags = {"现场促销：SceneController"})
@RestController
@RequestMapping("/v1/scene")
public class SceneController {

    @Resource
    private ShowActivityClient showActivityClient;

    @Resource
    private Sender sender;

    @ApiOperation(value = "获取现场活动分页列表", notes = "获取现场活动分页列表")
    @PostMapping(value = "/page")
    public Result<PageGrid<SceneInfo>> pageScene(@RequestBody PageParam<SceneInfo> page) {
        return showActivityClient.pageScene(page);
    }

    @ApiOperation(value = "获取现场活动详细信息", notes = "获取现场活动详细信息")
    @GetMapping(value = "/{sceneId}")
    public Result<SceneInfo> getSceneInfo(@PathVariable("sceneId") @NotBlank String sceneId) {
        return showActivityClient.getSceneInfo(sceneId);
    }

    @ApiOperation(value = "添加现场活动信息", notes = "添加现场活动信息")
    @PostMapping(value = "")
    public Result addScene(@RequestBody @Validated SceneInfo sceneInfo) {
        return showActivityClient.addScene(sceneInfo);
    }

    @ApiOperation(value = "修改现场活动信息", notes = "修改现场活动信息")
    @PatchMapping(value = "")
    public Result modifyScene(@RequestBody @Validated SceneInfo sceneInfo) {
        return showActivityClient.modifyScene(sceneInfo);
    }

    @ApiOperation(value = "获取现场活动商品分页列表", notes = "获取现场活动商品分页列表")
    @PostMapping(value = "/goods/page")
    public Result<PageGrid<SceneGoods>> pageGoods(@RequestBody PageParam<SceneGoods> page) {
        return showActivityClient.pageSceneGoods(page);
    }

    @ApiOperation(value = "获取现场活动商品详细信息", notes = "获取现场活动商品详细信息")
    @GetMapping(value = "/goods/{goodsId}")
    public Result<SceneGoods> getGoodsInfo(@PathVariable("goodsId") @NotBlank String goodsId) {
        return showActivityClient.getGoodsInfo(goodsId);
    }

    @ApiOperation(value = "添加现场活动商品信息", notes = "添加现场活动商品信息")
    @PostMapping(value = "/goods")
    public Result addGoods(@RequestBody @Validated SceneGoods goods) {
        return showActivityClient.addSceneGoods(goods);
    }

    @ApiOperation(value = "修改现场活动商品信息", notes = "修改现场活动商品信息")
    @PatchMapping(value = "/goods")
    public Result modifyGoods(@RequestBody @Validated SceneGoods goods) {
        return showActivityClient.modifySceneGoods(goods);
    }

    @ApiOperation(value = "获取用户订单分页列表", notes = "获取用户订单分页列表")
    @PostMapping(value = "/order/page")
    public Result<PageGrid<SceneOrder>> pageOrder(@RequestBody PageParam<SceneOrder> page) {
        return showActivityClient.pageSceneOrder(page);
    }

    @ApiOperation(value = "订单退款", notes = "订单退款")
    @PatchMapping(value = "/order/refund")
    public Result orderRefund(@RequestParam("orderNo") @NotBlank String orderNo) {
        ShowAssert.checkResult(showActivityClient.modifySceneOrderStatus(orderNo, OrderStatus.REFUNDED));
        sender.sceneOrderRefund(orderNo);
        return new Result<>();
    }
}
