package com.szhengzhu.service;

import com.szhengzhu.bean.member.MemberTicket;
import com.szhengzhu.bean.ordering.param.GiveParam;
import com.szhengzhu.bean.ordering.vo.UserTicketVo;

import java.util.List;
import java.util.Map;

public interface UserTicketService {

    /**
     * 给用户赠送礼物
     *
     * @param giveParam
     * @return
     */
    void resGiveTicket(GiveParam giveParam);

    /**
     * 获取会员优惠券列表
     *
     * @param userId
     * @param status
     * @return
     */
    List<UserTicketVo> listUserTicket(String userId, Integer status);

    /**
     * 结账获取用户优惠券列表
     *
     * @param userId
     * @return
     */
    List<UserTicketVo> resUserTicket(String userId);

    /**
     * 小程序结账获取用户订单可用优惠券
     *
     * @param userId
     * @param indentId
     * @return
     */
    List<UserTicketVo> xlistUserTicketByIndent(String userId, String indentId);


    /**
     * 根据会员id查询优惠券
     *
     * @param markId
     * @return
     */
    List<MemberTicket> memberTicket(String markId);

    /**
     * 根据优惠券的id回收会员的优惠券
     *
     * @param markId
     * @return
     */
    void deleteMemberTicket(String markId);

    Integer queryTicketQuantity(String userId, String templateId);
}
