package com.szhengzhu.mapper;

import com.szhengzhu.bean.member.RechargeRefund;

public interface RechargeRefundMapper {

    int deleteByPrimaryKey(String markId);

    int insert(RechargeRefund record);

    int insertSelective(RechargeRefund record);

    RechargeRefund selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(RechargeRefund record);

    int updateByPrimaryKey(RechargeRefund record);
}