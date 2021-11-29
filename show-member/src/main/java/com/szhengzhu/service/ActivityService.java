package com.szhengzhu.service;

import com.szhengzhu.bean.member.Activity;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

/**
 * @author jehon
 */
public interface ActivityService {

    /**
     * 添加活动
     *
     * @param activity
     */
    void add(Activity activity);

    /**
     * 修改活动
     *
     * @param activity
     */
    void modify(Activity activity);

    /**
     * 根据主键删除活动信息
     *
     * @param markId
     * @return
     */
    void deleteByActivityId(String markId);

    /**
     * 获取活动分页列表
     *
     * @param pageParam
     * @return
     */
    PageGrid<Activity> page(PageParam<Activity> pageParam);

    /**
     * 根据主键markId获取信息
     *
     * @param code
     * @return
     */
    Activity getWellInfoByCode(String code);
}
