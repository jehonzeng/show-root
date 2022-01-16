package com.szhengzhu.controller;

import com.szhengzhu.feign.ShowGoodsClient;
import com.szhengzhu.bean.activity.SeckillInfo;
import com.szhengzhu.bean.wechat.vo.GoodsDetail;
import com.szhengzhu.bean.wechat.vo.SeckillDetail;
import com.szhengzhu.bean.wechat.vo.StockBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.service.SeckillService;
import com.szhengzhu.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jehon Zeng
 */
@Validated
@RestController
@RequestMapping("/seckill")
public class SeckillController {

    @Resource
    private SeckillService seckillService;

    @Resource
    private ShowGoodsClient showGoodsClient;

    @PostMapping(value = "/page")
    public PageGrid<SeckillInfo> pageSeckill(
            @RequestBody PageParam<SeckillInfo> seckillPage) {
        return seckillService.pageSeckill(seckillPage);
    }

    @PostMapping(value = "/add")
    public SeckillInfo addInfo(@RequestBody @Validated SeckillInfo seckillInfo) {
        return seckillService.addInfo(seckillInfo);
    }

    @PatchMapping(value = "/modify")
    public void modifyInfo(@RequestBody @Validated SeckillInfo seckillInfo) {
        seckillService.modifyInfo(seckillInfo);
    }

    @GetMapping(value = "/{markId}")
    public SeckillInfo getInfo(@PathVariable("markId") @NotBlank String markId) {
        return seckillService.getInfo(markId);
    }

    @PostMapping(value = "/fore/page")
    public PageGrid<Map<String, Object>> pageInfo(
            @RequestBody PageParam<String> pageParam) {
        return seckillService.pageInfo(pageParam);
    }

    @GetMapping(value = "/fore/detail/{markId}")
    public SeckillDetail getDetail(@PathVariable("markId") @NotBlank String markId,
                                   @RequestParam(value = "userId", required = false) String userId) {
        SeckillDetail seckillDetail = seckillService.getDetail(markId);
        Result<GoodsDetail> result = showGoodsClient.getGoodsDetail(seckillDetail.getGoodsId(),
                userId);
        if (result.isSuccess()) {
            seckillDetail.setCookerInfo(result.getData().getCookerInfo());
            seckillDetail.setImagePaths(result.getData().getImagePaths());
            seckillDetail.setJudges(result.getData().getJudges());
            seckillDetail.setServers(result.getData().getServers());
        }
        return seckillDetail;
    }

    @GetMapping(value = "/fore/stock")
    public Map<String, Object> getStock(@RequestParam("markId") @NotBlank String markId,
                                        @RequestParam(value = "addressId", required = false) String addressId) {
        ShowAssert.checkTrue(StringUtils.isEmpty(markId), StatusCode._4004);
        Map<String, Object> resultMap = new HashMap<>(4);
        int totalStock = 0;
        boolean isDelivery = true;
        SeckillInfo seckillInfo = seckillService.getInfo(markId);
        ShowAssert.checkNull(seckillInfo, StatusCode._4004);
        if (seckillInfo.getTotalStock() > 0) {
            Result<StockBase> stockResult = showGoodsClient.getGoodsStcokInfo(
                    seckillInfo.getGoodsId(), seckillInfo.getSpecificationIds(), addressId);
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
        seckillService.subStock(markId);
    }

    @GetMapping(value = "/stock/add")
    public void addStock(@RequestParam("markId") @NotBlank String markId) {
        seckillService.addStock(markId);
    }
}
