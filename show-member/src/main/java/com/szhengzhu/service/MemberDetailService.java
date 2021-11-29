package com.szhengzhu.service;

import com.szhengzhu.bean.member.MemberDetail;
import com.szhengzhu.bean.member.MemberRecord;
import com.szhengzhu.bean.member.param.MemberPaymentParam;
import com.szhengzhu.bean.member.param.MemberRecordParam;
import com.szhengzhu.core.PageGrid;
import com.szhengzhu.core.PageParam;

import java.util.Map;

/**
 * @author jehon
 */
public interface MemberDetailService {

    /**
     * 查询会员充值详细信息
     *
     * @param param 会员账户明细列表分页参数信息
     * @return
     */
    PageGrid<MemberDetail> pageDetail(PageParam<MemberDetail> param);

    /**
     * 获取用户充值消费详情
     *
     * @param pageParam
     * @return
     */
    PageGrid<MemberDetail> pageUserDetail(PageParam<String> pageParam);

    /**
     * 删除会员账户记录
     *
     * @param detailId
     * @return
     */
    void deleteDetail(String detailId, String userId);

    /**
     * 查询会员消费充值明细记录
     *
     * @param param
     * @return
     */
    PageGrid<MemberRecordParam> memberRecord(PageParam<MemberRecord> param);


    /**
     * 查询会员消费充值总额
     *
     * @param memberRecord
     * @return
     */
    Map<String, Object> memberDetailTotal(MemberRecord memberRecord);

    /**
     * 会员支付
     *
     * @param param
     * @return
     */
    PageGrid<MemberPaymentParam> memberPayment(PageParam<MemberRecord> param);

    /**
     * 删除会员消费明细
     *
     * @param detailId
     */
    void delete(String detailId);

    MemberDetail selectByMarkId(String markId);
}
