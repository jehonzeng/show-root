package com.szhengzhu.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.GoodsVoucher;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.wechat.vo.GoodsInfoVo;
import com.szhengzhu.bean.wechat.vo.JudgeBase;
import com.szhengzhu.bean.wechat.vo.StockBase;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.service.GoodsVoucherService;
import com.szhengzhu.util.StringUtils;

@RestController
@RequestMapping(value = "/goodsVouchers")
public class GoodsVoucherController {

    @Resource
    private GoodsVoucherService goodsVoucherService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<?> addGoodsVoucher(@RequestBody GoodsVoucher base) {
        return goodsVoucherService.addGoodsVoucher(base);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.PATCH)
    public Result<?> modifyGoodsVoucher(@RequestBody GoodsVoucher base) {
        return goodsVoucherService.editGoodsVoucher(base);
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<?> getGoodsVoucherPage(@RequestBody PageParam<GoodsVoucher> base) {
        return goodsVoucherService.getCouponPage(base);
    }

    @RequestMapping(value = "/combobox", method = RequestMethod.GET)
    public Result<List<Combobox>> listCombobox() {
        return goodsVoucherService.listCombobox();
    }

    @RequestMapping(value = "/{markId}", method = RequestMethod.GET)
    public Result<?> GoodsVoucherInfo(@PathVariable("markId") String markId) {
        return goodsVoucherService.getGoodsVoucherInfo(markId);
    }

    @RequestMapping(value = "/fore/detail", method = RequestMethod.GET)
    public Result<GoodsInfoVo> getVoucherDetail(@RequestParam("voucherId") String voucherId,
            @RequestParam(value = "userId", required = false) String userId) {
        if (StringUtils.isEmpty(voucherId))
            return new Result<>(StatusCode._4004);
        return goodsVoucherService.getVoucherDetail(voucherId, userId);
    }

    @RequestMapping(value = "/fore/judge", method = RequestMethod.GET)
    public Result<List<JudgeBase>> listVoucherJudge(@RequestParam("voucherId") String voucherId,
            @RequestParam(value = "userId", required = false) String userId) {
        if (StringUtils.isEmpty(voucherId))
            return new Result<>(StatusCode._4004);
        return goodsVoucherService.listVoucherJudge(voucherId, userId);
    }
    
    @RequestMapping(value = "/stock/info", method = RequestMethod.GET)
    public Result<StockBase> getStockInfo(@RequestParam("voucherId") String voucherId) {
        if (!StringUtils.isEmpty(voucherId))
            return new Result<>(StatusCode._4004);
        return goodsVoucherService.getStockInfo(voucherId);
    }

}
