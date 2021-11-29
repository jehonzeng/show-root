package com.szhengzhu.controller;

import com.szhengzhu.bean.activity.SceneGoods;
import com.szhengzhu.bean.activity.SceneInfo;
import com.szhengzhu.bean.activity.SceneItem;
import com.szhengzhu.bean.activity.SceneOrder;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.SceneService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

/**
 * @author Jehon Zeng
 */
@Validated
@RestController
@RequestMapping("/scene")
public class SceneController {

    @Resource
    private SceneService sceneService;

    @PostMapping(value = "/page")
    public PageGrid<SceneInfo> pageScene(@RequestBody PageParam<SceneInfo> page) {
        return sceneService.pageScene(page);
    }

    @GetMapping(value = "/{sceneId}")
    public SceneInfo getSceneInfo(@PathVariable("sceneId") @NotBlank String sceneId) {
        return sceneService.getSceneInfo(sceneId);
    }

    @PostMapping(value = "/add")
    public void addScene(@RequestBody @Validated SceneInfo sceneInfo) {
        sceneService.addScene(sceneInfo);
    }

    @PatchMapping(value = "/modify")
    public void modifyScene(@RequestBody @Validated SceneInfo sceneInfo) {
        sceneService.modifyScene(sceneInfo);
    }

    @PostMapping(value = "/goods/page")
    public PageGrid<SceneGoods> pageGoods(@RequestBody PageParam<SceneGoods> page) {
        return sceneService.pageGoods(page);
    }

    @GetMapping(value = "/goods/{goodsId}")
    public SceneGoods getGoodsInfo(@PathVariable("goodsId") @NotBlank String goodsId) {
        return sceneService.getGoodsInfo(goodsId);
    }

    @PostMapping(value = "/goods/add")
    public void addGoods(@RequestBody @Validated SceneGoods goods) {
        sceneService.addGoods(goods);
    }

    @PatchMapping(value = "/goods/modify")
    public void modifyGoods(@RequestBody @Validated SceneGoods goods) {
        sceneService.modifyGoods(goods);
    }

    @PostMapping(value = "/order/page")
    public PageGrid<SceneOrder> pageOrder(@RequestBody PageParam<SceneOrder> page) {
        return sceneService.pageOrder(page);
    }

    @GetMapping(value = "/goods/list")
    public List<SceneGoods> listForeGoods() {
        return sceneService.listForeGoods();
    }

    @PostMapping(value = "/order/create")
    public Map<String, Object> createOrder(@RequestBody @NotEmpty List<String> goodsIdList,
            @RequestParam("userId") @NotBlank String userId) {
        return sceneService.createOrder(goodsIdList, userId);
    }

    @GetMapping(value = "/order/item/unreceive")
    public List<SceneItem> listUnReceiveGoods(@RequestParam("userId") @NotBlank String userId) {
        return sceneService.listUnReceiveGoods(userId);
    }

    @GetMapping(value = "/order/item/receive")
    public List<SceneItem> listReceiveGoods(@RequestParam("userId") @NotBlank String userId) {
        return sceneService.listReceiveGoods(userId);
    }

    @GetMapping(value = "/order/info")
    public SceneOrder getOrderInfo(@RequestParam("orderNo") @NotBlank String orderNo) {
        return sceneService.getOrderInfo(orderNo);
    }

    @GetMapping(value = "/order/status")
    public void modifyOrderStatus(@RequestParam("orderNo") @NotBlank String orderNo,
            @RequestParam("orderStatus") @NotBlank String orderStatus) {
        sceneService.modifyOrderStatus(orderNo, orderStatus);
    }

    @PatchMapping(value = "/order/item/receive")
    public List<String> receiveGoods(@RequestBody @NotEmpty List<String> idsList, @RequestParam("userId") @NotBlank String userId) {
        return sceneService.receiveGoods(idsList, userId);
    }
}
