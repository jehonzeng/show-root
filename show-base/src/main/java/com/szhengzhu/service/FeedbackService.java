package com.szhengzhu.service;

import com.szhengzhu.bean.base.FeedbackInfo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;

public interface FeedbackService {

    /**
     * 获取问题反馈分页列表
     * 
     * @date 2019年3月5日 下午12:06:33
     * @param feedbackPage
     * @return
     */
    Result<PageGrid<FeedbackInfo>> pageFeedback(PageParam<FeedbackInfo> feedbackPage);
    
    /**
     * 管理员处理问题反馈
     * 
     * @date 2019年3月5日 下午12:08:54
     * @param 
     * @return
     */
    Result<?> processFeedback(FeedbackInfo base);
    
    /**
     * 用户添加反馈信息
     * 
     * @date 2019年7月24日 上午11:18:59
     * @param feedbackInfo
     * @return
     */
    Result<?> add(FeedbackInfo feedbackInfo);
}
