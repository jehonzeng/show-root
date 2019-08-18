package com.szhengzhu.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.base.FeedbackInfo;
import com.szhengzhu.bean.user.UserInfo;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.client.ShowBaseClient;
import com.szhengzhu.client.ShowUserClient;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"问题反馈：FeedbackController"})
@RestController
@RequestMapping("/v1/feedback")
public class FeedbackController {

    @Resource
    private ShowBaseClient showBaseClient;
    
    @Resource
    private ShowUserClient showUserClient;
    
    @ApiOperation(value = "获取反馈类型下拉列表", notes = "获取反馈类型下拉列表")
    @RequestMapping(value = "/type/list", method = RequestMethod.GET)
    public Result<List<Combobox>> listFeedbackType() {
        return showBaseClient.listFeedbckType("FB");
    }
    
    @ApiOperation(value = "用户提交反馈信息", notes = "用户提交反馈信息")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result<?> add(HttpServletRequest request, @RequestBody FeedbackInfo feedbackInfo) {
        String token = request.getHeader("Show-Token");
        Result<UserInfo> userResult = showUserClient.getUserByToken(token);
        if (userResult.isSuccess()) {
            String userId = userResult.getData().getMarkId();
            feedbackInfo.setCreator(userId);
            return showBaseClient.addFeedback(feedbackInfo);
        }
        return new Result<>(StatusCode._4012);
    }
}
