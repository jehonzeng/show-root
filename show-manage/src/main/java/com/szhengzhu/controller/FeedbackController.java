package com.szhengzhu.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.base.FeedbackInfo;
import com.szhengzhu.client.ShowBaseClient;
import com.szhengzhu.common.Commons;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"问题反馈管理：FeedbackController"})
@RestController
@RequestMapping("/v1/feedback")
public class FeedbackController {
    
    @Resource
    private ShowBaseClient showBaseClient;
    
    @ApiOperation(value = "获取问题反馈分页列表", notes = "获取问题反馈分页列表")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<FeedbackInfo>> pageFeedback(@RequestBody PageParam<FeedbackInfo> feedbackPage) {
        return showBaseClient.pageFeedback(feedbackPage);
    }
    
    @ApiOperation(value = "管理员处理问题反馈信息", notes = "管理员处理问题反馈信息")
    @RequestMapping(value = "/process", method = RequestMethod.PATCH)
    public Result<?> processFeedback(HttpSession session,@RequestBody FeedbackInfo base) {
        String processor = (String) session.getAttribute(Commons.SESSION);
        base.setProcessor(processor);
        return showBaseClient.processFeedback(base);
    }
}
