package com.szhengzhu.controller;

import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.bean.goods.GoodsVoucher;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.GoodsVoucherVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Administrator
 */
@Validated
@Api(tags = {"商品券管理：GoodsVoucherController"})
@RestController
@RequestMapping(value = "/v1/goodsVouchers")
public class GoodsVoucherController {

    @Resource
    private ShowGoodsClient showGoodsClient;

    @ApiOperation(value = "添加商品券信息", notes = "保存商品券信息")
    @PostMapping(value = "")
    public Result<GoodsVoucher> addGoodsVoucher(@RequestBody @Validated GoodsVoucher base) {
        return showGoodsClient.addGoodsVoucher(base);
    }

    @ApiOperation(value = "修改商品券信息", notes = "编辑商品券信息")
    @PatchMapping(value = "")
    public Result<GoodsVoucher> editGoodsVoucher(@RequestBody @Validated GoodsVoucher base) {
        return showGoodsClient.modifyGoodsVoucher(base);
    }

    @ApiOperation(value = "商品券分页", notes = "根据不同条件获取商品券分页")
    @PostMapping(value = "/page")
    public Result<PageGrid<GoodsVoucherVo>> GoodsVoucherPage(@RequestBody PageParam<GoodsVoucher> base) {
        return showGoodsClient.getGoodsVoucherPage(base);
    }

    @ApiOperation(value = "商品券下拉列表", notes = "商品券下拉列表")
    @GetMapping(value = "/combobox")
    public Result<List<Combobox>> listCouponCombobox() {
        return showGoodsClient.listCouponCombobox();
    }

    @ApiOperation(value = "根据id获取商品券信息", notes = "根据id获取商品券信息")
    @GetMapping(value = "/{markId}")
    public Result<GoodsVoucher> GoodsVoucherInfo(@PathVariable("markId") @NotBlank String markId) {
        return showGoodsClient.getGoodsVoucherInfo(markId);
    }

}
