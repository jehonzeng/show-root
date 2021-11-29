package com.szhengzhu.service;

import com.szhengzhu.bean.member.MemberActivity;
import com.szhengzhu.bean.member.ReceiveTicket;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;

public interface MemberActivityService {
    /**
     * 通过ID查询单条数据
     *
     * @param markId 主键
     * @return 实例对象
     */
    MemberActivity queryActivityById(String markId);

    /**
     * 查询会员活动信息
     *
     * @param memberActivity 实例对象
     * @return 对象列表
     */
    List<MemberActivity> memberActivity(MemberActivity memberActivity);

    /**
     * 分页查询会员活动信息
     *
     * @param param 分页对象
     * @return 分页列表
     */
    PageGrid<MemberActivity> memberActivityByPage(PageParam<MemberActivity> param);

    /**
     * 新增会员活动信息
     *
     * @param memberActivity 实例对象
     */
    void addActivity(MemberActivity memberActivity);

    /**
     * 修改会员活动信息
     *
     * @param memberActivity 实例对象
     */
    void modifyActivity(MemberActivity memberActivity);

    List<ReceiveTicket> queryById(String receiveId);

    void deleteActivity(String markId);
}
