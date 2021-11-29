package com.szhengzhu.controller;

import com.szhengzhu.client.ShowGoodsClient;
import com.szhengzhu.bean.goods.GoodsVoucher;
import com.szhengzhu.bean.order.UserVoucher;
import com.szhengzhu.bean.wechat.vo.VoucherBase;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.service.UserVoucherService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * 用户菜品券类
 *
 * @author Jehon Zeng
 */
@Validated
@RestController
@RequestMapping(value = "/vouchers")
public class UserVoucherController {

    @Resource
    private UserVoucherService userVoucherService;

    @Resource
    private ShowGoodsClient showGoodsClient;

    @GetMapping(value = "/list/{userId}")
    public List<VoucherBase> listByUser(@PathVariable("userId") @NotBlank String userId) {
        return userVoucherService.listByUser(userId);
    }

    @GetMapping(value = "/map")
    public Map<String, UserVoucher> mapByIds(@RequestParam(value = "vouchers") List<String> vouchers) {
        return userVoucherService.mapByIds(vouchers);
    }

    @GetMapping(value = "/send")
    public void sendGoodsVoucher(@RequestParam("userId") @NotBlank String userId,
                                      @RequestParam("voucherId") @NotBlank String voucherId) {
        Result<GoodsVoucher> voucherResult = showGoodsClient.getGoodsVoucherInfo(voucherId);
        ShowAssert.checkTrue(!voucherResult.isSuccess(), StatusCode._5009);
        userVoucherService.addGoodsVoucher(userId, voucherResult.getData());
    }
}
