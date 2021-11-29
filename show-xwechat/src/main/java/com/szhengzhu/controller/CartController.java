package com.szhengzhu.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.szhengzhu.client.ShowOrderingClient;
import com.szhengzhu.client.ShowUserClient;
import com.szhengzhu.bean.CartServerData;
import com.szhengzhu.bean.user.UserInfo;
import com.szhengzhu.bean.xwechat.param.CartParam;
import com.szhengzhu.core.Contacts;
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
import java.util.Map;

/**
 * @author Administrator
 */
@Validated
@Api(tags = {"购物车：CartController"})
@RestController
@RequestMapping("/v1/cart")
public class CartController {

    @Resource
    private ShowUserClient showUserClient;

    @Resource
    private ShowOrderingClient showOrderingClient;

    @Resource
    private Redis redis;

    @ApiOperation(value = "添加购物车商品")
    @PostMapping(value = "")
    public Result<String> addDetail(HttpServletRequest request, @RequestBody @Validated CartParam cartDetail) {
        UserInfo userInfo = UserUtils.getUserInfoByToken(request, showUserClient);
        cartDetail.setUserId(userInfo.getMarkId());
        Result<String> result = showOrderingClient.addCart(cartDetail);
        if (result.isSuccess()) {
            sendCartMessage(userInfo, "addCart", cartDetail.getTableId(), cartDetail);
        }
        return result;
    }

    @ApiOperation(value = "修改购物车商品")
    @PutMapping(value = "")
    public Result modifyDetail(HttpServletRequest request, @RequestBody @Validated CartParam cartParam) {
        UserInfo userInfo = UserUtils.getUserInfoByToken(request, showUserClient);
        cartParam.setUserId(userInfo.getMarkId());
        Result<Object> result = showOrderingClient.modifyCart(cartParam);
        if (result.getCode().equals(Contacts.SUCCESS_CODE)) {
            String operate = "modifyCart";
            if (cartParam.getQuantity().equals(0)) {
                operate = "deleteCart";
            }
            sendCartMessage(userInfo, operate, cartParam.getTableId(), cartParam);
        }
        return result;
    }

    @ApiOperation(value = "清空购物车", notes = "清空明细")
    @DeleteMapping(value = "/{tableId}")
    public Result clearCart(HttpServletRequest request, @PathVariable("tableId") @NotBlank String tableId) {
        UserInfo userInfo = UserUtils.getUserInfoByToken(request, showUserClient);
        sendCartMessage(userInfo, "clearCart", tableId, null);
        return showOrderingClient.clearCart(tableId);
    }

    @ApiOperation(value = "获取购物车商品列表")
    @GetMapping(value = "/{tableId}")
    public Result<Map<String, Object>> listDetailByTable(@PathVariable("tableId") @NotBlank String tableId) {
        return showOrderingClient.listCartByTable(tableId);
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
}
