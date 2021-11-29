package com.szhengzhu.service;

import com.szhengzhu.bean.member.MatchPay;

/**
 * @author jehon
 */
public interface MatchPayService {

    /**
     * 记录将要投票的记录
     *
     * @param matchId
     * @param quantity
     * @param userId
     * @return
     */
    MatchPay matchPay(String matchId, Integer quantity, String userId);
}
