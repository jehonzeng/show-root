package com.szhengzhu.controller;

import com.szhengzhu.bean.base.FeedbackInfo;
import com.szhengzhu.bean.rpt.IndexDisplay;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.service.FeedbackService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Validated
@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Resource
    private FeedbackService feedbackService;

    @PostMapping(value = "/page")
    public PageGrid<FeedbackInfo> pageFeedback(@RequestBody PageParam<FeedbackInfo> feedbackPage) {
        return feedbackService.pageFeedback(feedbackPage);
    }

    @PatchMapping(value = "/process")
    public void processFeedback(@RequestBody @Validated FeedbackInfo base) {
        feedbackService.processFeedback(base);
    }

    @PostMapping(value = "/add")
    public void add(@RequestBody @Validated FeedbackInfo feedbackInfo) {
        feedbackService.add(feedbackInfo);
    }

    @GetMapping(value = "/backend/index")
    public List<IndexDisplay> getIndexFeedbackCount() {
        return feedbackService.getIndexFeedbackCount();
    }
}
