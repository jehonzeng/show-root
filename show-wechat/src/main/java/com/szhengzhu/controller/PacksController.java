package com.szhengzhu.controller;

import com.szhengzhu.client.ShowBaseClient;
import com.szhengzhu.client.ShowUserClient;
import com.szhengzhu.bean.user.UserToken;
import com.szhengzhu.core.Result;
import com.szhengzhu.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

/**
 * @author Administrator
 */
@Validated
@Api(tags = {"礼包主题：PacksController"})
@RestController
@RequestMapping(value = "/v1/packs")
public class PacksController {

    @Resource
    private ShowUserClient showUserClient;

    @Resource
    private ShowBaseClient showBaseClient;

    @ApiOperation(value = "领取安心大礼包", notes = "领取安心大礼包")
    @GetMapping(value = "/munual")
    public Result receivedCoupon(HttpServletRequest request, @RequestParam("markId") @NotBlank String markId) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        return showBaseClient.manualCoupon(markId, userToken.getUserId());
    }
}
