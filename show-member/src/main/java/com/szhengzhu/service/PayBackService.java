package com.szhengzhu.service;

import com.szhengzhu.bean.member.PayBack;

/**
 * @author jehon
 */
public interface PayBackService {

    /**
     * 小程序会员自助充值回滚
     *
     * @param payId
     */
    void modifyPayBack(String payId);

    /**
     * 记录支付
     *
     * @param payBack
     */
    void payBack(PayBack payBack);
}
