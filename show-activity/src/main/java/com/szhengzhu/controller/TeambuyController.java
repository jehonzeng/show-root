package com.szhengzhu.controller;

import com.szhengzhu.feign.ShowGoodsClient;
import com.szhengzhu.bean.activity.TeambuyInfo;
import com.szhengzhu.bean.wechat.vo.GoodsDetail;
import com.szhengzhu.bean.wechat.vo.StockBase;
import com.szhengzhu.bean.wechat.vo.TeambuyDetail;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.service.TeambuyService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping("/teambuy")
public class TeambuyController {

    @Resource
    private TeambuyService teambuyService;

    @Resource
    private ShowGoodsClient showGoodsClient;

    @PostMapping(value = "/page")
    public PageGrid<TeambuyInfo> pageTeambuy(@RequestBody PageParam<TeambuyInfo> teambuyPage) {
        return teambuyService.pageTeambuy(teambuyPage);
    }

    @PostMapping(value = "/add")
    public TeambuyInfo addInfo(@RequestBody @Validated TeambuyInfo teambuyInfo) {
        return teambuyService.addInfo(teambuyInfo);
    }

    @PatchMapping(value = "/modify")
    public void modifyInfo(@RequestBody @Validated TeambuyInfo teambuyInfo) {
        teambuyService.modifyInfo(teambuyInfo);
    }

    @GetMapping(value = "/{markId}")
    public TeambuyInfo getTeambuyInfo(@PathVariable("markId") @NotBlank String markId) {
        return teambuyService.getInfo(markId);
    }

    @PostMapping(value = "/fore/page")
    public PageGrid<Map<String, Object>> pageForeList(@RequestBody PageParam<String> pageParam) {
        return teambuyService.pageForeList(pageParam);
    }

    @GetMapping(value = "/fore/detail/{markId}")
    public TeambuyDetail getDetail(@PathVariable("markId") @NotBlank String markId, @RequestParam(value = "userId", required = false) String userId) {
        TeambuyDetail teambuyDetail = teambuyService.getDetail(markId);
        ShowAssert.checkNull(teambuyDetail, StatusCode._4004);
        Result<GoodsDetail> goodsResult = showGoodsClient.getGoodsDetail(teambuyDetail.getGoodsId(), userId);
        if (goodsResult.isSuccess()) {
            teambuyDetail.setCookerInfo(goodsResult.getData().getCookerInfo());
            teambuyDetail.setImagePaths(goodsResult.getData().getImagePaths());
            teambuyDetail.setJudges(goodsResult.getData().getJudges());
            teambuyDetail.setServers(goodsResult.getData().getServers());
        }
        return teambuyDetail;
    }

    @GetMapping(value = "/fore/stock")
    public Map<String, Object> getStock(@RequestParam("markId") @NotBlank String markId,
            @RequestParam(value = "addressId", required = false) String addressId) {
        Map<String, Object> resultMap = new HashMap<>();
        int totalStock = 0;
        boolean isDelivery = true;
        TeambuyInfo teambuyInfo = teambuyService.getInfo(markId);
        ShowAssert.checkNull(teambuyInfo, StatusCode._4004);
        if (teambuyInfo.getTotalStock() > 0) {
            Result<StockBase> stockResult = showGoodsClient.getGoodsStcokInfo(teambuyInfo.getGoodsId(),
                    teambuyInfo.getSpecificationIds(), addressId);
           //此处少！
            ShowAssert.checkTrue(!stockResult.isSuccess(), StatusCode._4004);
            totalStock = stockResult.getData().getCurrentStock();
            isDelivery = stockResult.getData().getIsDelivery();
        }
        resultMap.put("totalStock", totalStock);
        resultMap.put("isDelivery", isDelivery);
        return resultMap;
    }

    @GetMapping(value = "/stock/sub")
    public void subStock(@RequestParam("markId") @NotBlank String markId) {
        teambuyService.subStock(markId);
    }

    @GetMapping(value = "/stock/add")
    public void addStock(@RequestParam("markId") @NotBlank String markId) {
        teambuyService.addStock(markId);
    }
}
