package com.szhengzhu.service;

import com.szhengzhu.bean.activity.*;
import com.szhengzhu.bean.vo.ActHistoryVo;
import com.szhengzhu.bean.vo.ActivityModel;
import com.szhengzhu.bean.wechat.vo.ActRelation;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

public interface ActivityService {

    /**
     *
     *
     * @param base
     * @return
     * @date 2019年10月16日
     */
    PageGrid<ActivityModel> getPage(PageParam<ActivityInfo> base);

    /**
     *
     *
     * @param base
     * @return
     * @date 2019年10月16日
     */
    ActivityInfo addActivity(ActivityInfo base);

    /**
     *
     *
     * @param base
     * @return
     * @date 2019年10月16日
     */
    ActivityInfo modifyActivity(ActivityInfo base);

    /**
     *
     *
     * @param markId
     * @return
     * @date 2019年10月16日
     */
    ActivityInfo getActivityInfo(String markId);

    /**
     *
     *
     * @param base
     * @return
     * @date 2019年10月16日
     */
    PageGrid<ActivityGift> getActGiftPage(PageParam<ActivityGift> base);

    /**
     *
     *
     * @param base
     * @return
     * @date 2019年10月16日
     */
    ActivityGift addActGift(ActivityGift base);

    /**
     *
     *
     * @param base
     * @return
     * @date 2019年10月16日
     */
    ActivityGift updateActGift(ActivityGift base);

    /**
     *
     *
     * @param markId
     * @return
     * @date 2019年10月16日
     */
    ActivityGift getActGiftById(String markId);

    /**
     *
     *
     * @param base
     * @return
     * @date 2019年10月16日
     */
    ActivityRule updateActRule(ActivityRule base);

    /**
     *
     *
     * @param base
     * @return
     * @date 2019年10月16日
     */
    PageGrid<ActHistoryVo> getHistoryPage(PageParam<ActivityHistory> base);

    /**
     *
     *
     * @param history
     * @return
     * @date 2019年10月16日
     */
    void addActHistory(ActivityHistory history);

    /**
     *
     *
     * @param base
     * @return
     * @date 2019年10月16日
     */
    ParticipantRelation helpRelation(ActRelation base);

    /**
     *
     *
     * @param base
     * @return
     * @date 2019年10月29日
     */
    HelpLimit addHelpPointRule(HelpLimit base);

    /**
     *
     *
     * @param base
     * @return
     * @date 2019年10月29日
     */
    HelpLimit updateHelpPointRule(HelpLimit base);

    /**
     * @param base
     * @return
     */
    PageGrid<HelpLimit> getHelpPointRulePage(PageParam<HelpLimit> base);

}
