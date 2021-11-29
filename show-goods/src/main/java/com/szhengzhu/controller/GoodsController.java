package com.szhengzhu.controller;

import com.szhengzhu.bean.goods.GoodsInfo;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.GoodsVo;
import com.szhengzhu.bean.wechat.vo.GoodsBase;
import com.szhengzhu.bean.wechat.vo.GoodsDetail;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.GoodsService;
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
@RequestMapping(value = "/goods")
public class GoodsController {
    
    @Resource
    private GoodsService goodsService;

    @PostMapping(value = "/add")
    public GoodsInfo addGoods(@RequestBody @Validated GoodsInfo goodsInfo) {
        return goodsService.addGoods(goodsInfo);
    }

    @PatchMapping(value = "/edit")
    public GoodsInfo modifyGoods(@RequestBody GoodsInfo goodsInfo) {
        return goodsService.modifyGoods(goodsInfo);
    }

    @PatchMapping(value = "/editStatus")
    public GoodsInfo modifyGoodsStatus(@RequestBody GoodsInfo base)  {
        return goodsService.modifyGoodsStatus(base);
    }

    @PostMapping(value = "/page")
    public PageGrid<GoodsVo> page(@RequestBody PageParam<GoodsInfo> base) {
        return goodsService.getPage(base);
    }

    @GetMapping(value = "/{markId}")
    public GoodsInfo goodsInfo(@PathVariable("markId") @NotBlank String markId) {
        return goodsService.getGoodsInfo(markId);
    }

    @GetMapping(value = "/combobox")
    public List<Combobox> selectCombobox() {
        return goodsService.listCombobox();
    }

    @GetMapping(value = "/listNotColumn")
    public List<Combobox> listNotColumn() {
        return goodsService.getListNotColumn();
    }

    @GetMapping(value = "/listNotLabel")
    public List<Combobox> listNotLabel(@RequestParam("labelId") @NotBlank String labelId) {
        return goodsService.getListNotLabel(labelId);
    }

    @GetMapping(value = "/servesIn")
    public List<String> listGoodsServer(@RequestParam("goodsId") @NotBlank String goodsId) {
        return goodsService.listInnerServer(goodsId);
    }

    @PostMapping(value = "/addBatchServe")
    public void saveBatchGoodsServe(@RequestBody BatchVo base) {
        goodsService.addBatchServer(base);
    }

    @PostMapping(value = "/deleteBatchServe")
    public void deleteBatchGoodsServer(@RequestBody BatchVo base) {
        goodsService.moveBatchServer(base);
    }

    @GetMapping(value = "/listNotSpecial")
    public List<Combobox> getListNotSpecial() {
        return goodsService.getListNotSpecial();
    }

    @GetMapping(value = "/listNotIcon")
    public List<Combobox> getListNotIcon() {
        return goodsService.getListNotIcon();
    }

    @PostMapping(value = "/column/page")
    public PageGrid<GoodsVo> getPageByColumn(@RequestBody PageParam<GoodsInfo> base) {
        return goodsService.getPageByColumn(base);
    }

    @PostMapping(value = "/label/page")
    public PageGrid<GoodsVo> getPageByLabel(@RequestBody PageParam<GoodsInfo> base) {
        return goodsService.getPageByLabel(base);
    }

    @PostMapping(value = "/icon/page")
    public PageGrid<GoodsVo> getPageByIcon(@RequestBody PageParam<GoodsInfo> base) {
        return goodsService.getPageByIcon(base);
    }

    @PostMapping(value = "/special/page")
    public PageGrid<GoodsVo> getPageBySpecial(@RequestBody PageParam<GoodsInfo> base) {
        return goodsService.getPageBySpecial(base);
    }

    @GetMapping(value = "/fore/index/recommend")
    public List<GoodsBase> listRecommend(
            @RequestParam(value = "userId", required = false) String userId) {
        return goodsService.listRecommend(userId);
    }

    @GetMapping(value = "/fore/detail")
    public GoodsDetail getGoodsDetail(@RequestParam("goodsId") @NotBlank String goodsId,
            @RequestParam(value = "userId", required = false) String userId) throws Exception {
        return goodsService.getGoodsDetail(goodsId, userId);
    }
}
