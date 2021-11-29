package com.szhengzhu.service;

import com.szhengzhu.bean.member.MemberAccount;
import com.szhengzhu.bean.member.MemberByType;
import com.szhengzhu.bean.member.MemberDetail;
import com.szhengzhu.bean.member.param.MemberDetailParam;
import com.szhengzhu.bean.member.param.RechargeParam;
import com.szhengzhu.bean.member.vo.MemberAccountVo;
import com.szhengzhu.bean.member.vo.MemberTicketVo;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface MemberService {

    /**
     * 查询充值会员列表
     *
     * @param param 会员列表分页参数信息
     * @return
     */
    PageGrid<MemberAccount> pageAccount(PageParam<MemberByType> param);

    /**
     * 用户充值
     *
     * @param account
     * @return
     */
    String addAccount(MemberAccount account);

    /**
     * 修改账户人员基本信息
     *
     * @param base
     * @return
     */
    MemberAccount modifyAccount(MemberAccount base);

    /**
     * 获取会员详细信息
     *
     * @param markId 会员账户id
     * @return
     */
    MemberAccount getInfo(String markId);

    /**
     * 获取会员显示信息
     *
     * @param markId
     * @return
     */
    MemberAccountVo getVoInfoById(String markId);

    /**
     * 根据userId获取会员信息
     *
     * @param userId
     * @return
     */
    MemberAccount getInfoByUserId(String userId);

    /**
     * 手机号、会员号获取会员信息
     *
     * @param phone
     * @param accountNo
     * @return
     */
    List<MemberAccountVo> getInfoByNoOrPhone(String phone, String accountNo);

    /**
     * 会员充值
     *
     * @param detailParam
     * @return
     */
    MemberAccount recharge(MemberDetailParam detailParam);

    /**
     * 会员充值（后台模板充值）
     *
     * @param param
     * @return
     */
    void rechargeByRule(RechargeParam param);

    /**
     * 小程序用户充值
     *
     * @param ruleId
     * @param userId
     * @param xopenId
     * @return
     */
    MemberDetail rechargeByRule(String ruleId, String userId, String xopenId, BigDecimal indentTotal);


    /**
     * 会员消费
     *
     * @param detail
     * @return
     */
    String consume(MemberDetail detail);

    /**
     * 每分钟生成一次条形码唯一值
     *
     * @param userId
     * @return
     */
    String createBarMark(String userId);

    /**
     * 扫会员条形码获取会员主键
     *
     * @param mark
     * @param phone
     * @return
     */
    String scanCodeByMark(String mark, String phone);

    /**
     * 点餐平台结账获取会员信息
     *
     * @param memberId
     * @return
     */
    MemberTicketVo resInfoById(String memberId);

    /**
     * 获取用户余额
     *
     * @param userId
     * @return
     */
    BigDecimal getTotalByUserId(String userId);

    /**
     * 查询会员信息
     *
     * @return
     */
    Map<String, Object> getMemberInfo();

    BigDecimal selectMemberDiscount(String markId);
}
