package com.szhengzhu.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.szhengzhu.bean.base.FeedbackInfo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.service.FeedbackService;
import com.szhengzhu.util.StringUtils;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Resource
    private FeedbackService feedbackService;
    
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public Result<PageGrid<FeedbackInfo>> pageFeedback(@RequestBody PageParam<FeedbackInfo> feedbackPage) {
        return feedbackService.pageFeedback(feedbackPage);
    }
    
    @RequestMapping(value = "/process", method = RequestMethod.PATCH)
    public Result<?> processFeedback(@RequestBody FeedbackInfo base) {
        return feedbackService.processFeedback(base);
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<?> add(@RequestBody FeedbackInfo feedbackInfo) {
        if (feedbackInfo == null || StringUtils.isEmpty(feedbackInfo.getTypeId()) || StringUtils.isEmpty(feedbackInfo.getContent()) || StringUtils.isEmpty(feedbackInfo.getCreator()))
            return new Result<>(StatusCode._4004);
        return feedbackService.add(feedbackInfo);
    }
}
