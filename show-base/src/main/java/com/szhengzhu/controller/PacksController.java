package com.szhengzhu.controller;

import com.szhengzhu.client.ShowOrderClient;
import com.szhengzhu.bean.base.PacksInfo;
import com.szhengzhu.bean.base.PacksItem;
import com.szhengzhu.bean.order.UserCoupon;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.PacksVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.PacksService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping(value = "/packs")
public class PacksController {

    @Resource
    private PacksService packsService;

    @Resource
    private ShowOrderClient showOrderClient;

    @SuppressWarnings("unchecked")
    @GetMapping(value = "/manual")
    public void manualCoupon(@RequestParam("markId") @NotBlank String markId, @RequestParam("userId") @NotBlank String userId) {
        List<UserCoupon> list = packsService.manualCoupon(markId, userId);
        showOrderClient.receivedPacksCoupon(list);
    }

    @PostMapping(value = "/add")
    public PacksInfo addPacks(@RequestBody PacksInfo base) {
        return packsService.addPacks(base);
    }

    @PatchMapping(value = "/update")
    public PacksInfo modifyPacks(@RequestBody PacksInfo base) {
        return packsService.modifyPacks(base);
    }

    @PostMapping(value = "/page")
    public PageGrid<PacksInfo> packsPage(@RequestBody PageParam<PacksInfo> base) {
        return packsService.getPacksPage(base);
    }

    @PostMapping(value = "/item/page")
    public PageGrid<PacksVo> packsItemPage(@RequestBody PageParam<PacksItem> base) {
        return packsService.getPacksItemPage(base);
    }

    @PostMapping(value = "/item/batch")
    public void batchPacksTemplate(@RequestBody BatchVo base) {
        packsService.batchPacksTemplate(base);
    }

    @PatchMapping(value = "/item/update")
    public PacksItem updatePacksTeplate(@RequestBody PacksItem base) {
        return packsService.updatePacksTemplate(base);
    }

    @GetMapping(value = "/{markId}")
    public PacksInfo getPacksInfo(@PathVariable("markId") @NotBlank String markId) {
        return packsService.getPacksInfo(markId);
    }
}
