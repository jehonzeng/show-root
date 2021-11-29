package com.szhengzhu.service;

import com.szhengzhu.bean.member.MatchInfo;
import com.szhengzhu.bean.member.param.ExchangeParam;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.List;
import java.util.Map;

/**
 * @author jehon
 */
public interface MatchService {

    /**
     * 获取竞赛活动分页列表
     *
     * @param param
     * @return
     */
    PageGrid<MatchInfo> page(PageParam<MatchInfo> param);

    /**
     * 添加竞赛活动
     *
     * @param matchInfo
     */
    void add(MatchInfo matchInfo);

    /**
     * 修改竞赛活动
     *
     * @param matchInfo
     */
    void modify(MatchInfo matchInfo);

    /**
     * 查询活动列表
     *
     * @return
     */
    List<Map<String, Object>> list();

    /**
     * 竞赛活动关联队伍
     *
     * @param teamIds
     * @param matchId
     */
    void addItem(String matchId, List<String> teamIds);

    /**
     * 获取会员领券识别码
     *
     * @param matchId
     * @param userId
     * @return
     */
    Map<String, Object> getUserExchangeMark(String matchId, String userId);

    /**
     * 扫码获取
     *
     * @param mark
     * @return
     */
    Map<String, Object> scanCodeByMark(String mark);

    /**
     * 用户兑换券
     *
     * @param exchangeParam
     */
    void exchange(ExchangeParam exchangeParam);
}
