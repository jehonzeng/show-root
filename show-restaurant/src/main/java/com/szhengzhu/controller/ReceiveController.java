package com.szhengzhu.controller;

import com.szhengzhu.client.ShowMemberClient;
import com.szhengzhu.bean.member.PendDishes;
import com.szhengzhu.bean.member.ReceiveDishes;
import com.szhengzhu.bean.member.ReceiveRecord;
import com.szhengzhu.bean.member.vo.ReceiveVo;
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
@RestController
@RequestMapping("/v1/receive")
@Api(tags = "领取活动：ReceiveController")
public class ReceiveController {

    @Resource
    private ShowMemberClient showMemberClient;

    @ApiOperation(value = "查询领取菜品详细信息")
    @PostMapping(value = "/info/{markId}")
    public Result<List<ReceiveVo>> selectByDish(@PathVariable("markId") @NotBlank String markId) {
        return showMemberClient.selectByDish(markId);
    }

    @ApiOperation(value = "修改待领取信息")
    @PatchMapping(value = "/pend/modify")
    public Result modifyPend(@RequestBody PendDishes pendDishes) {
        return showMemberClient.modifyPend(pendDishes);
    }

    @ApiOperation(value = "查询待领取信息")
    @PostMapping(value = "/pend")
    public Result<List<PendDishes>> queryPend(@RequestBody PendDishes pendDishes) {
        return showMemberClient.queryPend(pendDishes);
    }

    @ApiOperation(value = "查询领取菜品信息")
    @PostMapping(value = "/info")
    public Result<PageGrid<ReceiveVo>> queryReceive(PageParam<ReceiveDishes> param) {
        return showMemberClient.queryReceive(param);
    }

    @ApiOperation(value = "添加领取菜品信息")
    @PostMapping(value = "/info/add")
    public Result addReceive(@RequestBody @Validated ReceiveDishes receiveDishes) {
        return showMemberClient.addReceive(receiveDishes);
    }

    @ApiOperation(value = "修改领取菜品信息")
    @PatchMapping(value = "/info/modify")
    public Result modifyReceive(@RequestBody ReceiveDishes receiveDishes) {
        return showMemberClient.modifyReceive(receiveDishes);
    }

    @ApiOperation(value = "查询动态领取记录表")
    @PostMapping(value = "/Record")
    public Result<List<ReceiveRecord>> queryRecord(@RequestBody ReceiveRecord receiveRecord) {
        return showMemberClient.queryRecord(receiveRecord);
    }

    @ApiOperation(value = "修改动态领取记录表")
    @PatchMapping(value = "/Record/modify")
    public Result modifyRecord(@RequestBody ReceiveRecord receiveRecord) {
        return showMemberClient.modifyRecord(receiveRecord);
    }

    @ApiOperation(value = "用户领取菜品券")
    @PostMapping(value = "/ticket/{markId}")
    public Result receiveTicket(@PathVariable("markId") @NotBlank String markId) {
        return showMemberClient.receiveTicket(markId);
    }
}
