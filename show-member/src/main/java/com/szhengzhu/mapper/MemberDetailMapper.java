package com.szhengzhu.mapper;

import com.szhengzhu.bean.member.MemberDetail;
import com.szhengzhu.bean.member.MemberRecord;
import com.szhengzhu.bean.member.param.MemberPaymentParam;
import com.szhengzhu.bean.member.param.MemberRecordParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MemberDetailMapper {

    int deleteByPrimaryKey(String markId);

    int insert(MemberDetail record);

    int insertSelective(MemberDetail record);

    MemberDetail selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(MemberDetail record);

    int updateByPrimaryKey(MemberDetail record);

    List<MemberDetail> selectByAccount(@Param("accountId") String accountId);

    List<MemberDetail> selectByUserId(@Param("userId") String userId);

    /**
     * 查询会员消费充值明细记录
     * @param memberRecord
     * @return
     */
    List<MemberRecordParam> memberRecord(MemberRecord memberRecord);

    /**
     * 查询会员消费充值总额
     *
     * @param memberRecord
     * @return
     */
    Map<String, Object> memberDetailTotal(MemberRecord memberRecord);

    /**
     * 会员支付
     * @param memberRecord
     * @return
     */
    List<MemberPaymentParam> memberPayment(MemberRecord memberRecord);

    /**
     * 查询会员人数和消费充值金额
     * @return
     */
    Map<String,Object> selectMemberInfo();
}
