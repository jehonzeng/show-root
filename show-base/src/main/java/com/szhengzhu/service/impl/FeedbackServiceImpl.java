package com.szhengzhu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szhengzhu.bean.base.FeedbackInfo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.core.Result;
import com.szhengzhu.core.StatusCode;
import com.szhengzhu.mapper.FeedbackInfoMapper;
import com.szhengzhu.service.FeedbackService;
import com.szhengzhu.util.IdGenerator;
import com.szhengzhu.util.TimeUtils;

@Service("feedbackService")
public class FeedbackServiceImpl implements FeedbackService {

    @Resource
    private FeedbackInfoMapper feedbackMapper;
    
    @Override
    public Result<PageGrid<FeedbackInfo>> pageFeedback(PageParam<FeedbackInfo> feedbackPage) {
        PageHelper.startPage(feedbackPage.getPageIndex(), feedbackPage.getPageSize());
        PageHelper.orderBy(feedbackPage.getSidx() + " " + feedbackPage.getSort());
        List<FeedbackInfo> list = feedbackMapper.selectByExampleSelective(feedbackPage.getData());
        PageInfo<FeedbackInfo> pageInfo = new PageInfo<>(list);
        return new Result<>(new PageGrid<>(pageInfo));
    }

    @Override
    public Result<?> processFeedback(FeedbackInfo base) {
        if (base == null || base.getMarkId() == null) {
            return new Result<>(StatusCode._4004);
        }
        base.setProcessTime(TimeUtils.today());
        base.setServerStatus(true);
        feedbackMapper.updateByPrimaryKeySelective(base);
        return new Result<>();
    }

    @Override
    public Result<?> add(FeedbackInfo feedbackInfo) {
        IdGenerator generator = IdGenerator.getInstance();
        feedbackInfo.setMarkId(generator.nexId());
        feedbackInfo.setCreateTime(TimeUtils.today());
        feedbackInfo.setServerStatus(false);
        feedbackMapper.insertSelective(feedbackInfo);
        return new Result<>();
    }

}
