package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.base.FeedbackInfo;
import com.szhengzhu.bean.rpt.IndexDisplay;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.mapper.FeedbackInfoMapper;
import com.szhengzhu.service.FeedbackService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 */
@Service("feedbackService")
public class FeedbackServiceImpl implements FeedbackService {

    @Resource
    private FeedbackInfoMapper feedbackMapper;

    @Override
    public PageGrid<FeedbackInfo> pageFeedback(PageParam<FeedbackInfo> feedbackPage) {
        PageMethod.startPage(feedbackPage.getPageIndex(), feedbackPage.getPageSize());
        PageMethod.orderBy(feedbackPage.getSidx() + " " + feedbackPage.getSort());
        List<FeedbackInfo> list = feedbackMapper.selectByExampleSelective(feedbackPage.getData());
        PageInfo<FeedbackInfo> pageInfo = new PageInfo<>(list);
        return new PageGrid<>(pageInfo);
    }

    @Override
    public void processFeedback(FeedbackInfo base) {
        base.setProcessTime(DateUtil.date());
        base.setServerStatus(true);
        int count = feedbackMapper.updateByPrimaryKeySelective(base);
        if(count == 1 ) {
            //推送反馈消息
        }
    }

    @Override
    public void add(FeedbackInfo feedbackInfo) {
        Snowflake snowflake = IdUtil.getSnowflake(1,1);
        feedbackInfo.setMarkId(snowflake.nextIdStr());
        feedbackInfo.setCreateTime(DateUtil.date());
        feedbackInfo.setServerStatus(false);
        feedbackMapper.insertSelective(feedbackInfo);
    }

    @Override
    public List<IndexDisplay> getIndexFeedbackCount() {
        return feedbackMapper.selectIndexFeebackCount();
    }

}
