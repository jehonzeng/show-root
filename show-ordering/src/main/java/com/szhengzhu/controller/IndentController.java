package com.szhengzhu.controller;

import com.szhengzhu.annotation.NoRepeatSubmit;
import com.szhengzhu.bean.ordering.Indent;
import com.szhengzhu.bean.ordering.IndentInfo;
import com.szhengzhu.bean.ordering.UserIndent;
import com.szhengzhu.bean.ordering.param.DetailDiscountParam;
import com.szhengzhu.bean.ordering.param.DetailParam;
import com.szhengzhu.bean.ordering.param.IndentPageParam;
import com.szhengzhu.bean.ordering.param.IndentParam;
import com.szhengzhu.bean.ordering.vo.IndentBaseVo;
import com.szhengzhu.bean.ordering.vo.IndentVo;
import com.szhengzhu.bean.xwechat.vo.IndentModel;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.rabbitmq.Sender;
import com.szhengzhu.service.IndentService;
import com.szhengzhu.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Validated
@RestController
@RequestMapping("/indent")
public class IndentController {

    @Resource
    private Sender sender;

    @Resource
    private IndentService indentService;

    @PostMapping(value = "/create/res")
    @NoRepeatSubmit
    public List<String> createBatch(@RequestBody @Validated IndentParam indentParam) {
        return indentService.createBatch(indentParam);
    }

    @PatchMapping(value = "/modify/res")
    @NoRepeatSubmit
    public void modify(@RequestBody @Validated DetailParam detailParam) {
        indentService.modifyDetail(detailParam);
    }

    @GetMapping(value = "/confirm/res")
    public List<String> confirm(@RequestParam("tableId") @NotBlank String tableId,
                                @RequestParam("employeeId") @NotBlank String employeeId) {
        return indentService.confirm(tableId, employeeId);
    }

    @GetMapping(value = "/create/{tableId}/x")
    @NoRepeatSubmit
    public Result<String> create(@PathVariable("tableId") @NotBlank String tableId, @RequestParam("userId") String userId) {
        return new Result<>(indentService.create(tableId, userId));
    }

    @PostMapping(value = "/page/x")
    public PageGrid<IndentModel> pageBase(@RequestBody PageParam<String> param) {
        ShowAssert.checkTrue(StringUtils.isEmpty(param.getData()), StatusCode._4004);
        return indentService.pageBase(param);
    }

    @GetMapping(value = "/model/{indentId}")
    public IndentModel getIndentModel(@PathVariable("indentId") @NotBlank String indentId) {
        return indentService.getIndentModel(indentId);
    }

    @GetMapping(value = "/info/n/{indentNo}")
    public Indent getIndentByIndentNo(@PathVariable("indentNo") @NotBlank String indentNo) {
        return indentService.getInfoByNo(indentNo);
    }

    @GetMapping(value = "/info/{indentId}")
    public Indent getInfo(@PathVariable("indentId") String indentId) {
        return indentService.getInfo(indentId);
    }

    @GetMapping(value = "/status")
    public void modifyIndentStatus(@RequestParam("indentId") String indentId,
                                   @RequestParam("status") String status) {
        indentService.modifyIndentStatus(indentId, status);
    }

    @GetMapping(value = "/detail/list/{indentId}")
    public IndentBaseVo listDetailById(@PathVariable("indentId") @NotBlank String indentId) {
        return indentService.listDetailById(indentId);
    }

    @PostMapping(value = "/page/res")
    public PageGrid<IndentVo> page(@RequestBody PageParam<IndentPageParam> pageParam) {
        return indentService.page(pageParam);
    }

    @GetMapping(value = "/bill/{indentId}/res")
    @NoRepeatSubmit
    public List<String> bill(@PathVariable("indentId") @NotBlank String indentId, @RequestParam("employeeId") @NotBlank String employeeId) {
        return indentService.bill(indentId, employeeId);
    }

    @GetMapping(value = "/cancel/bill/{indentId}/res")
    @NoRepeatSubmit
    public Result<String> cancelBill(@PathVariable("indentId") @NotBlank String indentId,
                                     @RequestParam("employeeId") @NotBlank String employeeId) {
        return new Result<>(indentService.cancelBill(indentId, employeeId));
    }

    @DeleteMapping(value = "/detail/{detailId}/res")
    @NoRepeatSubmit
    public List<String> delete(@PathVariable("detailId") @NotBlank String detailId,
                               @RequestParam("employeeId") @NotBlank String employeeId) {
        return indentService.deleteDetail(detailId, employeeId);
    }

    @PatchMapping(value = "/detail/discount/res")
    @NoRepeatSubmit
    public void discount(@RequestBody @Validated DetailDiscountParam discountParam) {
        indentService.detailDiscount(discountParam);
    }

    @GetMapping(value = "/member/bind/res")
    public void bindMember(@RequestParam("indentId") @NotBlank String indentId,
                           @RequestParam("memberId") @NotBlank String memberId) {
        indentService.bindMember(indentId, memberId);
    }

    @GetMapping(value = "/detail/comm/x")
    public List<Map<String, Object>> listIndentComm(@RequestParam("indentId") @NotBlank String indentId) {
        return indentService.listIndentComm(indentId);
    }

    @GetMapping(value = "/info/by/table")
    public Indent getInfoByTable(@RequestParam("tableId") @NotBlank String tableId) {
        return indentService.getInfoByTable(tableId);
    }

    @GetMapping(value = "/cost/total")
    public BigDecimal getCostTotal(@RequestParam("indentId") @NotBlank String indentId) {
        return indentService.getCostTotal(indentId);
    }

    @GetMapping(value = "/user/list")
    public List<String> listIndentUser(@RequestParam("indentId") @NotBlank String indentId) {
        return indentService.listIndentUser(indentId);
    }

    @GetMapping(value = "/select/markId")
    public IndentInfo selectById(@RequestParam("markId") @NotBlank String markId) {
        return indentService.selectById(markId);
    }

    @GetMapping(value = "/user")
    public UserIndent userIndent(@RequestParam("userId") @NotBlank String userId) {
        return indentService.userIndent(userId);
    }

    @GetMapping(value = "/member/discount")
    public BigDecimal getMemberDiscountTotal(@RequestParam("indentId") @NotBlank String indentId) {
        return indentService.getMemberDiscountTotal(indentId);
    }
}
