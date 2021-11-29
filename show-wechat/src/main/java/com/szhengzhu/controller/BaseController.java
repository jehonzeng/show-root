package com.szhengzhu.controller;

import com.szhengzhu.client.ShowActivityClient;
import com.szhengzhu.client.ShowBaseClient;
import com.szhengzhu.client.ShowOrderClient;
import com.szhengzhu.client.ShowUserClient;
import com.szhengzhu.bean.user.UserToken;
import com.szhengzhu.bean.vo.DeliveryDate;
import com.szhengzhu.config.WechatConfig;
import com.szhengzhu.core.Result;
import com.szhengzhu.util.UserUtils;
import com.szhengzhu.util.WechatUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Api(tags = {"基础信息专题：BaseController"})
@RestController
@RequestMapping("/base")
public class BaseController {

    @Resource
    private WechatConfig wechatConfig;

    @Resource
    private ShowOrderClient showOrderClient;

    @Resource
    private ShowBaseClient showBaseClient;

    @Resource
    private ShowUserClient showUserClient;

    @Resource
    private ShowActivityClient showActivityClient;

    @ApiOperation(value = "获取配置签名信息", notes = "获取配置签名信息")
    @GetMapping(value = "/config")
    public Result<?> getConfig(HttpServletRequest request) {
        String url = request.getParameter("url");
        return new Result<>(WechatUtils.buildConfigInfo(wechatConfig, url));
    }

    @ApiOperation(value = "获取最近八天配送日期", notes = "获取最近八天配送日期")
    @GetMapping(value = "/deliverydate/list")
    public Result<List<DeliveryDate>> listDeliveryDate() {
        return showOrderClient.listDeliveryDate();
    }

    @ApiOperation(value = "获取地区下拉列表", notes = "获取地区下拉列表")
    @GetMapping(value = "/area/list/{version}")
    public Result<Map<String, Object>> listArea(@PathVariable("version") int version) {
        return showBaseClient.listAllArea(version);
    }

    @ApiOperation(value = "管理员查看下单详情信息", notes = "管理员查看下单详情信息")
    @GetMapping(value = "/order/detail")
    public Result<?> checkDetail(@RequestParam("orderNo") @NotBlank String orderNo) {
        return showOrderClient.getOrderDetail(orderNo, null);
    }

    /**
     * 获取活动信息(type:0新人优惠 1邀新有礼2其他活动)
     *
     * @param markId
     * @return
     * @date 2019年10月9日
     */
    @ApiOperation(value = "获取活动信息", notes = "获取活动信息")
    @GetMapping(value = "/actInfo")
    public Result<Object> getActNewPeopleInfo(HttpServletRequest req, @RequestParam("markId") @NotBlank String markId,
                                         @RequestParam("type") @NotNull Integer type) {
        // 有些活动可能存在需要获取用户信息的情况
        UserToken userToken = UserUtils.getUserTokenByToken(req, showUserClient);
        return showActivityClient.getActInfo(markId, type, userToken.getUserId());
    }

    @ApiOperation(value = "活动分享接口", notes = "活动分享接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "url", value = "分享链接地址", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "status", allowableValues = "false,true", value = "获取分享者状态（true：是；false：否）", dataType = "boolean", paramType = "query")})
    @GetMapping(value = "/share")
    public Object share(HttpServletRequest request, @RequestParam("status") @NotNull Boolean status,
                        @RequestParam("url") String url) {
        Map<String, String> map = new HashMap<>(8);
        if (Boolean.TRUE.equals(status)) {
            UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
            map.put("fatherId", userToken.getUserId());
        }
        // 验证签名获取权限
        map.putAll(WechatUtils.buildConfigInfo(wechatConfig, url));
        return new Result<Object>(map);
    }
}
