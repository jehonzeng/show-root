package com.szhengzhu.bean.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.szhengzhu.bean.wechat.vo.OrderModel;

import lombok.Data;

@Data
public class CalcData implements Serializable {

    private static final long serialVersionUID = 5841717740499700721L;

    private BigDecimal total;   // 优惠后  加运费  支付金额
    
    private BigDecimal firstTotal;  // 商品总金额
    
    private BigDecimal discount;  // 除使用优惠券优惠的优惠金额  
    
    private BigDecimal couponDiscount; // 优惠券优惠金额
    
    private OrderModel orderModel; // 商品列表
    
    private BigDecimal deliveryAmount; // 配送费
    
    private BigDecimal deliveryLimit; // 免邮门槛
}
