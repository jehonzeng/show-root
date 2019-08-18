package com.szhengzhu.bean.vo;

import java.io.Serializable;
import java.util.Map;

import com.szhengzhu.bean.order.UserAddress;
import com.szhengzhu.bean.order.UserCoupon;
import com.szhengzhu.bean.order.UserVoucher;

import lombok.Data;

@Data
public class CalcBase implements Serializable {
    
    private static final long serialVersionUID = 5727922952577353394L;
    
    private Map<String, UserVoucher> voucherMap;
    
    private UserCoupon coupon;
    
    private UserAddress address;
}
