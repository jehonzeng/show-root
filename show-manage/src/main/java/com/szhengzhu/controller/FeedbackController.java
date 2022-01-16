package com.szhengzhu.controller;

import com.szhengzhu.feign.ShowBaseClient;
import com.szhengzhu.bean.base.FeedbackInfo;
import com.szhengzhu.core.Contacts;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author Administrator
 */
@Api(tags = {"问题反馈管理：FeedbackController"})
@RestController
@RequestMapping("/v1/feedback")
public class FeedbackController {

    @Resource
    private ShowBaseClient showBaseClient;

    @ApiOperation(value = "获取问题反馈分页列表", notes = "获取问题反馈分页列表")
    @PostMapping(value = "/page")
    public Result<PageGrid<FeedbackInfo>> pageFeedback(@RequestBody PageParam<FeedbackInfo> feedbackPage) {
        return showBaseClient.pageFeedback(feedbackPage);
    }

    @ApiOperation(value = "管理员处理问题反馈信息", notes = "管理员处理问题反馈信息")
    @PatchMapping(value = "/process")
    public Result processFeedback(HttpSession session, @RequestBody FeedbackInfo base) {
        String processor = (String) session.getAttribute(Contacts.LJS_SESSION);
        base.setProcessor(processor);
        return showBaseClient.processFeedback(base);
    }
}
