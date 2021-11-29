package com.szhengzhu.handler.impl;

import com.szhengzhu.annotation.ActivityType;
import com.szhengzhu.bean.activity.ActivityInfo;
import com.szhengzhu.core.Result;
import com.szhengzhu.handler.AbstractActivity;
import com.szhengzhu.mapper.ActivityInfoMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Component
@ActivityType(value = "1")
public class InvitationActHandler extends AbstractActivity {
    
    @Resource
    private ActivityInfoMapper activityInfoMapper;
    
    @Override
    public Result<Object> getActBaseInfo(String markId, String userId) {
        ActivityInfo info = activityInfoMapper.selectByMark(markId);
        return new Result<>(info);
    }

}
