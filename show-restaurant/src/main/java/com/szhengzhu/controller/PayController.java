package com.szhengzhu.controller;

import cn.hutool.core.util.ObjectUtil;
import com.szhengzhu.bean.ordering.Pay;
import com.szhengzhu.bean.ordering.PayType;
import com.szhengzhu.bean.ordering.vo.PayBaseVo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.client.ShowOrderingClient;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Administrator
 */
@Validated
@Api(tags = "支付方式：PayController")
@RestController
@RequestMapping("/v1/pay")
public class PayController {

    @Resource
    private ShowOrderingClient showOrderingClient;

    @ApiOperation(value = "获取支付方式分页列表")
    @PostMapping(value = "/page")
    public Result<PageGrid<Pay>> page(HttpServletRequest req, @RequestBody PageParam<Pay> pageParam) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        Pay param = ObjectUtil.isNull(pageParam.getData()) ? new Pay() : pageParam.getData();
        param.setStoreId(storeId);
        pageParam.setData(param);
        return showOrderingClient.pagePay(pageParam);
    }

    @ApiOperation(value = "添加支付方式")
    @PostMapping(value = "")
    public Result<String> add(HttpServletRequest req, @RequestBody @Validated Pay pay) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        pay.setStoreId(storeId);
        return showOrderingClient.addPay(pay);
    }

    @ApiOperation(value = "修改支付方式")
    @PatchMapping(value = "")
    public Result modify(HttpServletRequest req, @RequestBody @Validated Pay pay) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        pay.setStoreId(storeId);
        return showOrderingClient.modifyPay(pay);
    }

    @ApiOperation(value = "删除支付方式")
    @DeleteMapping(value = "/{payId}")
    public Result delete(@PathVariable("payId") @NotBlank String payId) {
        return showOrderingClient.deletePay(payId);
    }

    @ApiOperation(value = "获取支付方式类型列表")
    @GetMapping(value = "/res/list")
    public Result<List<PayBaseVo>> resPayList(HttpServletRequest req) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        return showOrderingClient.resPayList(storeId);
    }

    @ApiOperation(value = "获取支付方式类型分页列表")
    @PostMapping(value = "/type/page")
    public Result<PageGrid<PayType>> pageType(HttpServletRequest req, @RequestBody PageParam<PayType> pageParam) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        PayType param = ObjectUtil.isNull(pageParam.getData()) ? new PayType() : pageParam.getData();
        param.setStoreId(storeId);
        pageParam.setData(param);
        return showOrderingClient.pagePayType(pageParam);
    }

    @ApiOperation(value = "添加支付方式类型")
    @PostMapping(value = "/type")
    public Result<String> addType(HttpServletRequest req, @RequestBody @Validated PayType payType) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        payType.setStoreId(storeId);
        return showOrderingClient.addPayType(payType);
    }

    @ApiOperation(value = "修改支付方式类型")
    @PatchMapping(value = "/type")
    public Result modifyType(@RequestBody @Validated PayType payType) {
        return showOrderingClient.modifyPayType(payType);
    }

    @ApiOperation(value = "删除支付方式类型")
    @DeleteMapping(value = "/type/{typeId}")
    public Result deleteType(@PathVariable("typeId") @NotBlank String typeId) {
        return showOrderingClient.deletePayType(typeId);
    }

    @ApiOperation(value = "获取支付方式类型键值对列表")
    @GetMapping(value = "/type/combobox")
    public Result<List<Combobox>> combobox(HttpServletRequest req) {
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        return showOrderingClient.comboboxPayType(storeId);
    }
}
