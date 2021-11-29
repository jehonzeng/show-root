package com.szhengzhu.service;

import com.szhengzhu.bean.member.MatchPrize;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

/**
 * @author jehon
 */
public interface MatchPrizeService {
    
    /**
     * 获取赛程奖池分页列表
     *
     * @param param
     * @return
     */
    PageGrid<MatchPrize> page(PageParam<MatchPrize> param);

    /**
     * 添加赛程奖池
     *
     * @param matchPrize
     */
    void add(MatchPrize matchPrize);

    /**
     * 修改赛程奖池
     *
     * @param matchInfo
     */
    void modify(MatchPrize matchPrize);
}
