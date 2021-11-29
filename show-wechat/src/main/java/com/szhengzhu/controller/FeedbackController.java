package com.szhengzhu.controller;

import com.szhengzhu.client.ShowBaseClient;
import com.szhengzhu.client.ShowUserClient;
import com.szhengzhu.bean.base.FeedbackInfo;
import com.szhengzhu.bean.user.UserToken;
import com.szhengzhu.bean.vo.Combobox;
import com.szhengzhu.core.Result;
import com.szhengzhu.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Jehon Zeng
 */
@Api(tags = { "反馈专题：FeedbackController" })
@RestController
@RequestMapping("/v1/feedback")
public class FeedbackController {

    @Resource
    private ShowBaseClient showBaseClient;

    @Resource
    private ShowUserClient showUserClient;

    @ApiOperation(value = "获取反馈类型下拉列表", notes = "获取反馈类型下拉列表")
    @GetMapping(value = "/type/list")
    public Result<List<Combobox>> listFeedbackType() {
        return showBaseClient.listComboboxByType("FB");
    }

    @ApiOperation(value = "用户提交反馈信息", notes = "用户提交反馈信息")
    @PostMapping(value = "")
    public Result<Object> add(HttpServletRequest request, @RequestBody FeedbackInfo feedbackInfo) {
        UserToken userToken = UserUtils.getUserTokenByToken(request, showUserClient);
        feedbackInfo.setCreator(userToken.getUserId());
        return showBaseClient.addFeedback(feedbackInfo);
    }
}
