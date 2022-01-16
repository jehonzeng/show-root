package com.szhengzhu.controller;

import com.szhengzhu.feign.ShowOrderClient;
import com.szhengzhu.bean.order.BackHistory;
import com.szhengzhu.bean.order.RefundBack;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/v1/back")
public class BackController {
    
    @Resource
    private ShowOrderClient showOrderClient;

    @ApiOperation(value = "用户下单回调信息分页列表", notes = "用户下单回调信息分页列表")
    @PostMapping(value = "/history/page")
    public Result<PageGrid<BackHistory>> pageOrderBack(@RequestBody PageParam<BackHistory> pageParam) {
        return showOrderClient.pageOrderBack(pageParam);
    }
    
    @ApiOperation(value = "订单退款信息分页列表", notes = "订单退款信息分页列表")
    @PostMapping(value = "/refund/page")
    public Result<PageGrid<RefundBack>> pageRefund(@RequestBody PageParam<RefundBack> pageParam) {
        return showOrderClient.pageRefundBack(pageParam);
    }
    
    @ApiOperation(value = "获取订单退款详细信息")
    @GetMapping(value = "/refund/{markId}")
    public Result<RefundBack> getRefundBackInfo(@PathVariable("markId") @NotBlank String markId) {
        return showOrderClient.getRefundBackInfo(markId);
    }
}
