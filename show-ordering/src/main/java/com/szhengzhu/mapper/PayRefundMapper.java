package com.szhengzhu.mapper;

import com.szhengzhu.bean.ordering.PayRefund;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface PayRefundMapper {
    
    int deleteByPrimaryKey(String markId);

    int insert(PayRefund record);

    int insertSelective(PayRefund record);

    PayRefund selectByPrimaryKey(String markId);

    int updateByPrimaryKeySelective(PayRefund record);

    int updateByPrimaryKey(PayRefund record);
    
    PayRefund selectByNo(@Param("refundNo") String refundNo);
    
    @Select("select refund_no from t_pay_refund where pay_id=#{payId} limit 1")
    String selectNoByPay(@Param("payId") String payId);
}