package com.szhengzhu.controller;

import com.szhengzhu.bean.order.BackHistory;
import com.szhengzhu.bean.order.OrderRecord;
import com.szhengzhu.bean.order.RefundBack;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.BackService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * 回调类
 *
 * @author Jehon Zeng
 */
@RestController
@RequestMapping("/back")
public class BackController {

    @Resource
    private BackService backService;

    @PostMapping(value = "/history/page")
    public PageGrid<BackHistory> pageOrderBack(@RequestBody PageParam<BackHistory> pageParam) {
        return backService.pageOrderBack(pageParam);
    }

    @PostMapping(value = "/history")
    public void addOrderBack(@RequestBody BackHistory backHistory) {
        backService.addOrderBack(backHistory);
    }

    @PostMapping(value = "/refund/page")
    public PageGrid<RefundBack> pageRefundBack(@RequestBody PageParam<RefundBack> pageParam) {
        return backService.pageRefundBack(pageParam);
    }

    @PostMapping(value = "/refund")
    public void addRefundBack(@RequestBody RefundBack refundBack) {
        backService.addRefundBack(refundBack);
    }

    @GetMapping(value = "/refund/{markId}")
    public RefundBack getRefundBackInfo(@PathVariable("markId") @NotBlank String markId) {
        return backService.getRefundBackInfo(markId);
    }

    @GetMapping(value = "/error")
    public void orderPayErrorBack(@RequestParam("orderNo") @NotBlank String orderNo, @RequestParam("userId") @NotBlank String userId) {
        backService.addErrorBack(orderNo, userId);
    }

    @PostMapping(value = "/fore/record")
    public void addOrderRecord(@RequestBody OrderRecord orderRecord) {
        backService.addOrderRecord(orderRecord);
    }
}
