package com.szhengzhu.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.goods.GoodsVoucher;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.bean.vo.GoodsVoucherVo;
import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "商品券管理：GoodsVoucherController" })
@RestController
@RequestMapping(value = "/v1/goodsVouchers")
public class GoodsVoucherController {
    
    @Resource
    private ShowGoodsClient showGoodsClient;
    
    @ApiOperation(value = "添加商品券信息", notes = "保存商品券信息")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result<GoodsVoucher> addGoodsVoucher(@RequestBody GoodsVoucher base) {
        return showGoodsClient.addGoodsVoucher(base);
    }

    @ApiOperation(value = "修改商品券信息", notes = "编辑商品券信息")
    @RequestMapping(value = "", method = RequestMethod.PATCH)
    public Result<GoodsVoucher> editGoodsVoucher(@RequestBody GoodsVoucher base) {
        return showGoodsClient.modifyGoodsVoucher(base);
    }

    @ApiOperation(value = "商品券分页", notes = "根据不同条件获取商品券分页")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<GoodsVoucherVo>> GoodsVoucherPage(@RequestBody PageParam<GoodsVoucher> base) {
        return showGoodsClient.getGoodsVoucherPage(base);
    }
    
    @ApiOperation(value = "商品券下拉列表", notes = "商品券下拉列表")
    @RequestMapping(value = "/combobox", method = RequestMethod.GET)
    public Result<List<Combobox>> listCouponCombobox() {
        return showGoodsClient.listCouponCombobox();
    }
    
    @ApiOperation(value = "根据id获取商品券信息", notes = "根据id获取商品券信息")
    @RequestMapping(value = "/{markId}",method = RequestMethod.GET)
    public Result<GoodsVoucher> GoodsVoucherInfo(@PathVariable("markId") String markId){
        return showGoodsClient.goodsVoucherInfo(markId);
        
    }

}
