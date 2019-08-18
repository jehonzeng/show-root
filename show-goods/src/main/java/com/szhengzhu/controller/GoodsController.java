package com.szhengzhu.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.GoodsInfo;
import com.szhengzhu.bean.vo.BatchVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.GoodsVo;
import com.szhengzhu.bean.wechat.vo.GoodsBase;
import com.szhengzhu.bean.wechat.vo.GoodsInfoVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.service.GoodsService;

@RestController
@RequestMapping(value = "/goods")
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<?> addGoods(@RequestBody GoodsInfo goodsInfo) {
        return goodsService.addGoods(goodsInfo);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.PATCH)
    public Result<?> modifyGoods(@RequestBody GoodsInfo goodsInfo) {
        return goodsService.editGoods(goodsInfo);
    }

    @RequestMapping(value = "/editStatus", method = RequestMethod.PATCH)
    public Result<?> editGoodsStatus(@RequestBody GoodsInfo base) {
        return goodsService.editGoodsStatus(base);
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<GoodsVo>> page(@RequestBody PageParam<GoodsInfo> base) {
        return goodsService.getPage(base);
    }

    @RequestMapping(value = "/{markId}", method = RequestMethod.GET)
    public Result<?> goodsInfo(@PathVariable("markId") String markId) {
        return goodsService.getGoodsInfo(markId);
    }

    @RequestMapping(value = "/combobox", method = RequestMethod.GET)
    public Result<List<Combobox>> selectCombobox() {
        return goodsService.listCombobox();
    }

    @RequestMapping(value = "/listNotColumn", method = RequestMethod.GET)
    public Result<List<Combobox>> listNotColumn() {
        return goodsService.getListNotColumn();
    }

    @RequestMapping(value = "/listNotLabel", method = RequestMethod.GET)
    public Result<List<Combobox>> listNotLabel(@RequestParam("labelId") String labelId) {
        return goodsService.getListNotLabel(labelId);
    }

    @RequestMapping(value = "/servesIn", method = RequestMethod.GET)
    public Result<?> listGoodsServer(@RequestParam("goodsId") String goodsId) {
        return goodsService.listInnerServer(goodsId);
    }

    @RequestMapping(value = "/addBatchServe", method = RequestMethod.POST)
    public Result<?> saveBatchGoodsServe(@RequestBody BatchVo base) {
        return goodsService.addBatchServer(base);
    }

    @RequestMapping(value = "/deleteBatchServe", method = RequestMethod.POST)
    public Result<?> deleteBatchGoodsServer(@RequestBody BatchVo base) {
        return goodsService.moveBatchServer(base);
    }

    @RequestMapping(value = "/listNotSpecial", method = RequestMethod.GET)
    public Result<List<Combobox>> getListNotSpecial() {
        return goodsService.getListNotSpecial();
    }

    @RequestMapping(value = "/listNotIcon", method = RequestMethod.GET)
    public Result<List<Combobox>> getListNotIcon() {
        return goodsService.getListNotIcon();
    }

    @RequestMapping(value = "/column/page", method = RequestMethod.POST)
    public Result<PageGrid<GoodsVo>> getPageByColumn(@RequestBody PageParam<GoodsInfo> base) {
        return goodsService.getPageByColumn(base);
    }

    @RequestMapping(value = "/label/page", method = RequestMethod.POST)
    public Result<PageGrid<GoodsVo>> getPageByLabel(@RequestBody PageParam<GoodsInfo> base) {
        return goodsService.getPageByLabel(base);
    }

    @RequestMapping(value = "/icon/page", method = RequestMethod.POST)
    public Result<PageGrid<GoodsVo>> getPageByIcon(@RequestBody PageParam<GoodsInfo> base) {
        return goodsService.getPageByIcon(base);
    }

    @RequestMapping(value = "/special/page", method = RequestMethod.POST)
    public Result<PageGrid<GoodsVo>> getPageBySpecial(@RequestBody PageParam<GoodsInfo> base) {
        return goodsService.getPageBySpecial(base);
    }

    @RequestMapping(value = "/fore/index/recommend", method = RequestMethod.GET)
    public Result<List<GoodsBase>> listRecommend(@RequestParam(value = "userId", required = false) String userId) {
        return goodsService.listRecommend(userId);
    }

    @RequestMapping(value = "/fore/detail", method = RequestMethod.GET)
    public Result<GoodsInfoVo> getGoodsDetail(@RequestParam("goodsId") String goodsId,
            @RequestParam(value = "userId", required = false) String userId) {
        return goodsService.getGoodsDetail(goodsId, userId);
    }

}
