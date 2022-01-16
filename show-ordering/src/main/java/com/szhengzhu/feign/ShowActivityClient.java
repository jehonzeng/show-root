package com.szhengzhu.feign;

import com.szhengzhu.bean.activity.*;
import com.szhengzhu.bean.vo.ActHistoryVo;
import com.szhengzhu.bean.vo.ActivityModel;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.wechat.vo.ActRelation;
import com.szhengzhu.bean.wechat.vo.SeckillDetail;
import com.szhengzhu.bean.wechat.vo.TeambuyDetail;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.exception.ExceptionAdvice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@FeignClient(name = "show-activity", fallback = ExceptionAdvice.class)
public interface ShowActivityClient {

    /**  活动 */
    @PostMapping(value = "/acts/add", consumes = Contacts.CONSUMES)
    Result<ActivityInfo> addAct(@RequestBody ActivityInfo base);

    @GetMapping(value = "/acts/{markId}")
    Result<ActivityInfo> getActivityInfo(@PathVariable("markId") String markId);

    @PatchMapping(value = "/acts/modify", consumes = Contacts.CONSUMES)
    Result<ActivityInfo> updateAct(@RequestBody ActivityInfo base);

    @PostMapping(value = "/acts/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<ActivityModel>> getActivityPage(@RequestBody PageParam<ActivityInfo> base);

    @GetMapping(value = "/acts/fore/info")
    Result<Object> getActInfo(@RequestParam("markId") String markId, @RequestParam("type") Integer type,
                              @RequestParam(value = "userId", required = false) String userId);

    @GetMapping(value = "/acts/manual/gift")
    Result manualGift(@RequestParam("markId") String markId,
                      @RequestParam("userId") String userId, @RequestParam("type") Integer type);

    @GetMapping(value = "/acts/auto/gift")
    Result autoReceiveGift(@RequestParam("activityId") String activityId,
                           @RequestParam("userId") String userId, @RequestParam("type") Integer type);

    @PostMapping(value = "/acts/history/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<ActHistoryVo>> getHistoryPage(@RequestBody PageParam<ActivityHistory> base);

    @PostMapping(value = "/acts/help/rel", consumes = Contacts.CONSUMES)
    Result helpRelation(@RequestBody ActRelation base);

    @PostMapping(value = "/acts/help/rule/add", consumes = Contacts.CONSUMES)
    Result addHelpPointRule(HelpLimit base);

    @PatchMapping(value = "/acts/help/rule/modify", consumes = Contacts.CONSUMES)
    Result<HelpLimit> updateHelpPointRule(@RequestBody HelpLimit base);

    @PostMapping(value = "/acts/help/rulePage", consumes = Contacts.CONSUMES)
    Result<PageGrid<HelpLimit>> getHelpPointRulePage(PageParam<HelpLimit> base);

    @PostMapping(value = "/acts/gift/add", consumes = Contacts.CONSUMES)
    Result<ActivityGift> addActGift(@RequestBody ActivityGift base);

    @GetMapping(value = "/acts/gift/{markId}")
    Result<ActivityGift> getActGiftById(@PathVariable("markId") String markId);

    @PatchMapping(value = "/acts/gift/modify", consumes = Contacts.CONSUMES)
    Result<ActivityGift> updateActGift(@RequestBody ActivityGift base);

    @PostMapping(value = "/acts/gift/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<ActivityGift>> getActGiftPage(@RequestBody PageParam<ActivityGift> base);

    @PatchMapping(value = "/acts/rule/modify", consumes = Contacts.CONSUMES)
    Result<ActivityRule> updateActRule(@RequestBody ActivityRule base);

    /** 礼品 */
    @PostMapping(value = "/gifts/add", consumes = Contacts.CONSUMES)
    Result<GiftInfo> addGift(@RequestBody GiftInfo base);

    @GetMapping(value = "/gifts/combobox")
    Result<List<Combobox>> getGiftCombobox();

    @GetMapping(value = "/gifts/{markId}")
    Result<GiftInfo> getGiftInfo(@PathVariable("markId") String markId);

    @PatchMapping(value = "/gifts/modify", consumes = Contacts.CONSUMES)
    Result<GiftInfo> updateGift(@RequestBody GiftInfo base);

    @PostMapping(value = "/gifts/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<GiftInfo>> getGiftPage(@RequestBody PageParam<GiftInfo> base);

    /** 扫码中奖 */
    @PostMapping(value = "/scanwin/add", consumes = Contacts.CONSUMES)
    Result addScanWin(@RequestBody ScanWin win);

    @GetMapping(value = "/scanwin/{markId}")
    Result<ScanWin> getScanWinInfo(@PathVariable("markId") String markId);

    @PatchMapping(value = "/scanwin/modify", consumes = Contacts.CONSUMES)
    Result modifyScanWin(@RequestBody ScanWin win);

    @PostMapping(value = "/scanwin/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<ScanWin>> pageScanWin(@RequestBody PageParam<ScanWin> page);

    @GetMapping(value = "/scanwin/win")
    Result<String> scanWin(@RequestParam("scanCode") String scanCode, @RequestParam("openId") String openId);

    /** 现场促销 */
    @PostMapping(value = "/scene/add", consumes = Contacts.CONSUMES)
    Result addScene(@RequestBody SceneInfo sceneInfo);

    @PatchMapping(value = "/scene/modify", consumes = Contacts.CONSUMES)
    Result modifyScene(@RequestBody SceneInfo sceneInfo);

    @GetMapping(value = "/scene/{sceneId}")
    Result<SceneInfo> getSceneInfo(@PathVariable("sceneId") String sceneId);

    @PostMapping(value = "/scene/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<SceneInfo>> pageScene(@RequestBody PageParam<SceneInfo> page);

    @GetMapping(value = "/scene/goods/{goodsId}")
    Result<SceneGoods> getGoodsInfo(@PathVariable("goodsId") String goodsId);

    @PostMapping(value = "/scene/goods/add", consumes = Contacts.CONSUMES)
    Result addSceneGoods(@RequestBody SceneGoods goods);

    @GetMapping(value = "/scene/goods/list")
    Result<List<SceneGoods>> listSceneGoods();

    @PatchMapping(value = "/scene/goods/modify", consumes = Contacts.CONSUMES)
    Result modifySceneGoods(@RequestBody SceneGoods goods);

    @PostMapping(value = "/scene/goods/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<SceneGoods>> pageSceneGoods(@RequestBody PageParam<SceneGoods> page);

    @PostMapping(value = "/scene/order/create", consumes = Contacts.CONSUMES)
    Result<Map<String, Object>> createSceneOrder(@RequestBody List<String> goodsIdList,
                                                 @RequestParam("userId") String userId);

    @GetMapping(value = "/scene/order/info")
    Result<SceneOrder> getSceneOrderInfo(@RequestParam("orderNo") String orderNo);

    @GetMapping(value = "/scene/order/status")
    Result modifyOrderStatus(@RequestParam("orderNo") String orderNo, @RequestParam("orderStatus") String orderStatus);

    @GetMapping(value = "/scene/order/item/unreceive")
    Result<List<SceneItem>> listSceneUnReceiveGoods(@RequestParam("userId") String userId);

    @GetMapping(value = "/scene/order/item/receive")
    Result<List<SceneItem>> listSceneReceiveGoods(@RequestParam("userId") String userId);

    @PatchMapping(value = "/scene/order/item/receive", consumes = Contacts.CONSUMES)
    Result<List<String>> receiveGoods(@RequestBody List<String> idsList, @RequestParam("userId") String userId);


    @PostMapping(value = "/scene/order/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<SceneOrder>> pageSceneOrder(@RequestBody PageParam<SceneOrder> page);

    @GetMapping(value = "/scene/order/status")
    Result modifySceneOrderStatus(@RequestParam("orderNo") String orderNo, @RequestParam("orderStatus") String orderStatus);

    /** 秒杀 */
    @PostMapping(value = "/seckill/add", consumes = Contacts.CONSUMES)
    Result<SeckillInfo> addSeckill(@RequestBody SeckillInfo seckillInfo);

    @GetMapping(value = "/seckill/{markId}")
    Result<SeckillInfo> getSeckillInfo(@PathVariable("markId") String markId);

    @PatchMapping(value = "/seckill/modify", consumes = Contacts.CONSUMES)
    Result<SeckillInfo> modifySeckill(@RequestBody SeckillInfo seckillInfo);

    @PostMapping(value = "/seckill/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<SeckillInfo>> pageSeckill(@RequestBody PageParam<SeckillInfo> seckillPage);

    @GetMapping(value = "/seckill/fore/detail/{markId}")
    Result<SeckillDetail> getSeckillDetail(@PathVariable("markId") String markId,
                                           @RequestParam(value = "userId", required = false) String userId);

    @PostMapping(value = "/seckill/fore/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<Map<String, Object>>> pageForeSeckill(@RequestBody PageParam<String> pageParam);

    @GetMapping(value = "/seckill/fore/stock")
    Result<Map<String, Object>> getSeckillStock(@RequestParam("markId") String markId,
                                                @RequestParam(value = "addressId", required = false) String addressId);

    @GetMapping(value = "/seckill/stock/add")
    Result addSeckillStock(@RequestParam("markId") String markId);

    @GetMapping(value = "/seckill/stock/sub")
    Result subSeckillStock(@RequestParam("markId") String markId);

    /** 团购 */
    @PostMapping(value = "/teambuy/add", consumes = Contacts.CONSUMES)
    Result<TeambuyInfo> addTeambuy(@RequestBody TeambuyInfo teambuyInfo);

    @GetMapping(value = "/teambuy/{markId}")
    Result<TeambuyInfo> getTeambuyInfo(@PathVariable("markId") String markId);

    @PatchMapping(value = "/teambuy/modify", consumes = Contacts.CONSUMES)
    Result<TeambuyInfo> modifyTeambuy(@RequestBody TeambuyInfo teambuyInfo);

    @PostMapping(value = "/teambuy/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<TeambuyInfo>> pageTeambuy(@RequestBody PageParam<TeambuyInfo> teambuyPage);

    @GetMapping(value = "/teambuy/fore/detail/{markId}")
    Result<TeambuyDetail> getTeambuyDetail(@PathVariable("markId") String markId,
                                           @RequestParam(value = "userId", required = false) String userId);

    @PostMapping(value = "/teambuy/fore/page", consumes = Contacts.CONSUMES)
    Result<PageGrid<Map<String, Object>>> pageForeTeambuy(@RequestBody PageParam<String> pageParam);

    @GetMapping(value = "/teambuy/fore/stock")
    Result<Map<String, Object>> getTeambuyStock(@RequestParam("markId") String markId,
                                                @RequestParam(value = "addressId", required = false) String addressId);

    @GetMapping(value = "/teambuy/stock/add")
    Result addTeambuyStock(@RequestParam("markId") String markId);

    @GetMapping(value = "/teambuy/stock/sub")
    Result subTeambuyStock(@RequestParam("markId") String markId);
}
