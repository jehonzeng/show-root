package com.szhengzhu.service;

import com.szhengzhu.bean.base.FeedbackInfo;
import com.szhengzhu.bean.rpt.IndexDisplay;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

public interface FeedbackService {

    /**
     * 获取问题反馈分页列表
     *
     * @date 2019年3月5日 下午12:06:33
     * @param feedbackPage
     * @return
     */
    PageGrid<FeedbackInfo> pageFeedback(PageParam<FeedbackInfo> feedbackPage);

    /**
     * 管理员处理问题反馈
     *
     * @date 2019年3月5日 下午12:08:54
     * @param
     * @return
     */
    void processFeedback(FeedbackInfo base);

    /**
     * 用户添加反馈信息
     *
     * @date 2019年7月24日 上午11:18:59
     * @param feedbackInfo
     * @return
     */
    void add(FeedbackInfo feedbackInfo);

    /**
     * 获取首页反馈统计信息
     *
     * @return
     * @date 2019年10月18日
     */
    List<IndexDisplay> getIndexFeedbackCount();
}
