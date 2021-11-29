package com.szhengzhu.controller;

import com.szhengzhu.client.ShowActivityClient;
import com.szhengzhu.client.ShowUserClient;
import com.szhengzhu.bean.user.UserInfo;
import com.szhengzhu.bean.wechat.vo.ActRelation;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.exception.ShowAssert;
import com.szhengzhu.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

/**
 * @author Terry
 */
@Validated
@Api(tags = {"商城活动专题：MallActController"})
@RestController
@RequestMapping(value = "/v1/acts")
public class MallActController {

    @Resource
    private ShowActivityClient showActivityClient;

    @Resource
    private ShowUserClient showUserClient;

    @ApiOperation(value = "邀新有礼", notes = "邀新有礼")
    @PostMapping(value = "/invited")
    public Result createRel(HttpServletRequest request, @RequestBody ActRelation base) {
        UserInfo user = UserUtils.getUserInfoByToken(request, showUserClient);
        long level = 24L * 7 * 60 * 60 * 1000;
        ShowAssert.checkTrue(System.currentTimeMillis() - user.getCreateTime().getTime() > level, StatusCode._4030);
        ShowAssert.checkTrue(user.getMarkId().equals(base.getFatherId()), StatusCode._4031);
        base.setSonId(user.getMarkId());
        return showActivityClient.helpRelation(base);
    }

    @ApiOperation(value = "手动领取奖品", notes = "手动领取奖品")
    @GetMapping(value = "/munual/gift")
    public Result manualActGift(HttpServletRequest request,
                                     @RequestParam("markId") @NotBlank String markId, @RequestParam("type") Integer type) {
        UserInfo user = UserUtils.getUserInfoByToken(request, showUserClient);
        // 注册时间一周以内用户 -> 新用户
        int level = 24 * 7 * 60 * 60 * 1000;
//        DateUtil.between(DateUtil.date(), user.getCreateTime(), DateUnit.WEEK);
        if (System.currentTimeMillis() - user.getCreateTime().getTime() > level) {
            return new Result<>(StatusCode._4030);
        }
        String userId = user.getMarkId();
        return showActivityClient.manualGift(markId, userId, type);
    }
}
