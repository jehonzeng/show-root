package com.szhengzhu.controller;

import com.szhengzhu.bean.member.IntegralExchange;
import com.szhengzhu.bean.member.IntegralExpire;
import com.szhengzhu.bean.member.TicketExchange;
import com.szhengzhu.client.ShowMemberClient;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Validated
@Api(tags = "积分：IntegralController")
@RestController
@RequestMapping("/v1/integral")
public class IntegralController {

    @Resource
    private ShowMemberClient showMemberClient;

    @ApiOperation(value = "根据id查询积分兑换菜品券（代金券）")
    @GetMapping(value = "/exchange/{markId}")
    public Result<IntegralExchange> queryByIntegralExchangeId(@PathVariable("markId") @NotBlank String markId) {
        return showMemberClient.queryByIntegralExchangeId(markId);
    }

    @ApiOperation(value = "查询积分兑换菜品券（代金券）列表")
    @PostMapping(value = "/exchange/list")
    public Result<List<IntegralExchange>> queryIntegralExchangeList(@RequestBody IntegralExchange integralExchange) {
        return showMemberClient.queryIntegralExchangeList(integralExchange);
    }

    @ApiOperation(value = "添加积分兑换菜品券（代金券）信息")
    @PostMapping(value = "/exchange")
    public Result addIntegralExchange(@RequestBody IntegralExchange integralExchange, HttpSession session) {
        integralExchange.setCreator((String) session.getAttribute(Contacts.RESTAURANT_USER));
        return showMemberClient.addIntegralExchange(integralExchange);
    }

    @ApiOperation(value = "修改积分兑换菜品券（代金券）")
    @PatchMapping(value = "/exchange")
    public Result modifyIntegralExchange(@RequestBody IntegralExchange integralExchange, HttpSession session) {
        integralExchange.setModifier((String) session.getAttribute(Contacts.RESTAURANT_USER));
        return showMemberClient.modifyIntegralExchange(integralExchange);
    }

    @ApiOperation(value = "分页展示用户用积分兑换菜品券（代金券）详情")
    @PostMapping(value = "/exchange/ticket/page")
    public Result<PageGrid<TicketExchange>> queryTicketExchangePage(@RequestBody PageParam<TicketExchange> param) {
        return showMemberClient.queryTicketExchangePage(param);
    }

    @ApiOperation(value = "查询积分过期信息详情")
    @GetMapping(value = "/expire/info")
    public Result<IntegralExpire> queryByIntegralExpireId() {
        return showMemberClient.queryByIntegralExpireId();
    }

    @ApiOperation(value = "添加积分过期信息详情（记录只有一条）")
    @PostMapping(value = "/expire")
    public Result addIntegralExpire(@RequestBody IntegralExpire integralExpire) {
        return showMemberClient.addIntegralExpire(integralExpire);
    }
}
