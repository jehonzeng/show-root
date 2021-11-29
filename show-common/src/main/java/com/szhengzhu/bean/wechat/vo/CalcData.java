package com.szhengzhu.bean.wechat.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class CalcData implements Serializable {

    private static final long serialVersionUID = 5841717740499700721L;

    /** 优惠后 加运费 支付金额 */
    private BigDecimal total; 

    /** 商品总金额 */
    private BigDecimal firstTotal; 

    /** 除使用优惠券优惠的优惠金额 */
    private BigDecimal discount; 

    /** 优惠券优惠金额 */
    private BigDecimal couponDiscount; 

    /** 商品列表 */
    private OrderModel orderModel; 

    /** 配送费 */
    private BigDecimal deliveryAmount; 

    /** 免邮门槛 */
    private BigDecimal deliveryLimit; 

    /** 计算满减门槛 */
    private BigDecimal limitPrice; 

    /** 满减金额 */
    private BigDecimal limitDiscount;
    
    /** 口令优惠 */
    private BigDecimal codeDiscount;
    
    /** 内部分享人员ID */
    private String managerId;
}
