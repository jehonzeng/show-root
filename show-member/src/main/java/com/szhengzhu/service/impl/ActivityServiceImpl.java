package com.szhengzhu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.szhengzhu.bean.member.Activity;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import com.szhengzhu.mapper.ActivityMapper;
import com.szhengzhu.service.ActivityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author jehon
 */
@Service("activityService")
public class ActivityServiceImpl implements ActivityService {

    @Resource
    private ActivityMapper activityMapper;

    @Override
    public void add(Activity activity) {
        if (ObjectUtil.isNotNull(activity.getCode()) && Boolean.TRUE.equals(activity.getStatus())) {
            activityMapper.updateStatus(activity.getCode(), false);
        }
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        activity.setMarkId(snowflake.nextIdStr());
        activity.setCreateTime(DateUtil.date());
        activityMapper.insertSelective(activity);
    }

    @Override
    public void modify(Activity activity) {
        if (ObjectUtil.isNotNull(activity.getCode()) && Boolean.TRUE.equals(activity.getStatus())) {
            activityMapper.updateStatus(activity.getCode(), false);
        }
        activity.setModifyTime(DateUtil.date());
        activityMapper.updateByPrimaryKeySelective(activity);
    }

    @Override
    public void deleteByActivityId(String markId) {
        activityMapper.deleteByPrimaryKey(markId);
    }

    @Override
    public PageGrid<Activity> page(PageParam<Activity> pageParam) {
        PageMethod.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        PageMethod.orderBy("create_time " + pageParam.getSort());
        PageInfo<Activity> pageInfo = new PageInfo<>(
                activityMapper.selectByExampleSelective(pageParam.getData()));
        return new PageGrid<>(pageInfo);
    }

    @Override
    public Activity getWellInfoByCode(String code) {
        return activityMapper.selectWellByCode(code);
    }
}
