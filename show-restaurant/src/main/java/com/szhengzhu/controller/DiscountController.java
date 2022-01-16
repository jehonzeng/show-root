package com.szhengzhu.controller;

import com.szhengzhu.feign.ShowOrderingClient;
import com.szhengzhu.bean.ordering.DiscountInfo;
import com.szhengzhu.bean.vo.Combobox;
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
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Administrator
 */
@Validated
@Api(tags = {"折扣优惠：DiscountController"})
@RestController
@RequestMapping(value = "/v1/discount")
public class DiscountController {

    @Resource
    private ShowOrderingClient showOrderingClient;

    @ApiOperation(value = "添加商品折扣信息", notes = "添加商品折扣信息")
    @PostMapping(value = "")
    public Result addDiscount(HttpSession session, @RequestBody @Validated DiscountInfo base) {
        base.setCreator((String) session.getAttribute(Contacts.RESTAURANT_USER));
        base.setStoreId((String) session.getAttribute(Contacts.RESTAURANT_STORE));
        return showOrderingClient.addDiscount(base);
    }

    @ApiOperation(value = "获取商品折扣分页列表")
    @PostMapping(value = "/page")
    public Result<PageGrid<DiscountInfo>> pageTemplate(@RequestBody PageParam<DiscountInfo> base) {
        return showOrderingClient.getDiscountPage(base);
    }

    @ApiOperation(value = "获取商品折扣信息")
    @GetMapping(value = "/{markId}")
    public Result<DiscountInfo> getDiscountInfo(@PathVariable("markId") @NotBlank String markId) {
        return showOrderingClient.getDiscountInfo(markId);
    }

    @ApiOperation(value = "修改商品折扣信息")
    @PatchMapping(value = "")
    public Result<Object> modifyDiscount(@RequestBody @Validated DiscountInfo base) {
        return showOrderingClient.modifyDiscount(base);
    }

    @ApiOperation(value = "删除商品折扣信息")
    @DeleteMapping(value = "/{discountId}")
    public Result<Object> deleteDiscount(@PathVariable("discountId") @NotBlank String discountId) {
        return showOrderingClient.deleteDiscount(discountId);
    }


    @ApiOperation(value = "批量修改商品折扣状态")
    @PatchMapping(value = "/batch/{status}")
    public Result modifyStatus(@RequestBody @NotEmpty String[] discountIds, @PathVariable("status") @NotNull Integer status) {
        return showOrderingClient.modifyDiscountStatus(discountIds, status);
    }

    @ApiOperation(value = "点餐平台：获取商品优惠方案")
    @GetMapping(value = "/res/combobox")
    public Result<List<Combobox>> listCombobox(HttpServletRequest req) {
        String employeeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_USER);
        String storeId = (String) req.getSession().getAttribute(Contacts.RESTAURANT_STORE);
        return showOrderingClient.listDiscountCombobox(employeeId, storeId);
    }

    @ApiOperation(value = "批量删除折扣信息")
    @PatchMapping(value = "/batch/delete")
    public Result deleteBatch(@RequestBody @NotEmpty String[] discountIds) {
        return showOrderingClient.modifyDiscountStatus(discountIds, -1);
    }
}
