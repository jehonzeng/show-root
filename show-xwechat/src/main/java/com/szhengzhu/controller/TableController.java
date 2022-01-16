package com.szhengzhu.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.szhengzhu.feign.ShowOrderingClient;
import com.szhengzhu.feign.ShowUserClient;
import com.szhengzhu.bean.CartServerData;
import com.szhengzhu.bean.user.UserInfo;
import com.szhengzhu.bean.xwechat.param.CartParam;
import com.szhengzhu.bean.xwechat.param.TableOpenParam;
import com.szhengzhu.bean.xwechat.vo.TableModel;
import com.szhengzhu.core.Result;
import com.szhengzhu.redis.Redis;
import com.szhengzhu.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Administrator
 */
@Validated
@Api(tags = { "餐桌：TableController" })
@RestController
@RequestMapping("/v1/table")
public class TableController {

    @Resource
    private Redis redis;

    @Resource
    private ShowOrderingClient showOrderingClient;

    @Resource
    private ShowUserClient showUserClient;

    @ApiOperation(value = "新建购物车", notes = "创建餐桌正购物状态，用户选择用餐人数操作时操作")
    @PostMapping(value = "/open")
    public Result openCartTable(HttpServletRequest request, @RequestBody @Validated TableOpenParam param) {
        UserInfo userInfo = UserUtils.getUserInfoByToken(request, showUserClient);
        param.setUserId(userInfo.getMarkId());
        return showOrderingClient.openTable(param);
    }

    @ApiOperation(value = "获取餐桌信息 ")
    @GetMapping(value = "/info")
    public Result<TableModel> getLjsTableInfo(HttpServletRequest request, @RequestParam("tableId") @NotBlank String tableId) {
        UserInfo userInfo = UserUtils.getUserInfoByToken(request, showUserClient);
        sendCartMessage(userInfo, "addUser", tableId, null);
        return showOrderingClient.getLjsTableInfo(tableId);
    }

    /**
     * 推送到该购物车用户
     *
     * @param userInfo
     * @param operate
     * @param tableId
     * @param cartParam
     */
    private void sendCartMessage(UserInfo userInfo, String operate, String tableId, CartParam cartParam) {
        if (!StrUtil.isEmpty(tableId)) {
            CartServerData data = CartServerData.builder().userId(userInfo.getMarkId()).nickName(userInfo.getNickName())
                    .headerImg(userInfo.getHeaderImg()).opt(operate).tableId(tableId).commodity(cartParam).build();
            redis.convertAndSend("sendCartMsg", JSON.toJSONString(data));
        }
    }

    @ApiOperation(value = "修改当前餐桌用餐人数 ")
    @GetMapping(value = "/x/mannum/modify")
    public Result modifyManNum(@RequestParam("tableId") @NotBlank String tableId, @RequestParam("manNum") @NotNull Integer manNum) {
        return showOrderingClient.modifyTableManNum(tableId, manNum);
    }

    @ApiOperation(value = "扫码获取餐桌id")
    @GetMapping(value = "/x/id")
    public Result<String> getTableId(@RequestParam("url") String url) {
        return showOrderingClient.getTableId(url);
    }
}
