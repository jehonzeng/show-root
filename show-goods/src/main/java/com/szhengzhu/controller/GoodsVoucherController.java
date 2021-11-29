package com.szhengzhu.controller;

import com.szhengzhu.bean.goods.GoodsVoucher;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.GoodsVoucherVo;
import com.szhengzhu.bean.wechat.vo.GoodsDetail;
import com.szhengzhu.bean.wechat.vo.JudgeBase;
import com.szhengzhu.bean.wechat.vo.StockBase;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.GoodsVoucherService;
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
@RequestMapping(value = "/goodsVouchers")
public class GoodsVoucherController {

    @Resource
    private GoodsVoucherService goodsVoucherService;

    @PostMapping(value = "/add")
    public GoodsVoucher addGoodsVoucher(@RequestBody @Validated GoodsVoucher base) {
        return goodsVoucherService.addGoodsVoucher(base);
    }

    @PatchMapping(value = "/edit")
    public GoodsVoucher modifyGoodsVoucher(@RequestBody @Validated GoodsVoucher base) {
        return goodsVoucherService.editGoodsVoucher(base);
    }

    @PostMapping(value = "/page")
    public PageGrid<GoodsVoucherVo> getGoodsVoucherPage(@RequestBody PageParam<GoodsVoucher> base) {
        return goodsVoucherService.getCouponPage(base);
    }

    @GetMapping(value = "/combobox")
    public List<Combobox> listCombobox() {
        return goodsVoucherService.listCombobox();
    }

    @GetMapping(value = "/{markId}")
    public GoodsVoucher GoodsVoucherInfo(@PathVariable("markId") @NotBlank String markId) {
        return goodsVoucherService.getGoodsVoucherInfo(markId);
    }

    @GetMapping(value = "/fore/detail")
    public GoodsDetail getVoucherDetail(@RequestParam("voucherId") @NotBlank String voucherId,
            @RequestParam(value = "userId", required = false) String userId) {
        return goodsVoucherService.getVoucherDetail(voucherId, userId);
    }

    @GetMapping(value = "/fore/judge")
    public List<JudgeBase> listVoucherJudge(@RequestParam("voucherId") @NotBlank String voucherId,
            @RequestParam(value = "userId", required = false) String userId) {
        return goodsVoucherService.listVoucherJudge(voucherId, userId);
    }

    @GetMapping(value = "/stock/info")
    public StockBase getStockInfo(@RequestParam("voucherId") @NotBlank String voucherId) {
        return goodsVoucherService.getStockInfo(voucherId);
    }

    @GetMapping(value = "/sub")
    public void subStock(@RequestParam("voucherId") String voucherId, @RequestParam("quantity") Integer quantity) {
        goodsVoucherService.subStock(voucherId, quantity);
    }

    @GetMapping(value = "/add")
    public void addStock(@RequestParam("voucherId") String voucherId, @RequestParam("quantity") Integer quantity) {
        goodsVoucherService.addStock(voucherId, quantity);
    }
}
