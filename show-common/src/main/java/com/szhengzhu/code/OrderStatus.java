package com.szhengzhu.code;

/**
 * @author Jehon Zeng
 */
public interface OrderStatus {

    /**  未支付 */
    String NO_PAY = "OT01";
    
    /**  已支付 */
    String PAID = "OT02";
    
    /**  备货中 */
    String STOCKING = "OT03";
     
    /**   配送中 */
    String IN_DISTRIBUTION = "OT04";
    
    /**   已送达 */
    String ARRIVED = "OT05";
    
    /**  已评价 */
    String EVALUATED = "OT06";
    
    /**  申请退款 */
    String APPLY_REFUND = "OT07";
    
    /** 退款中 */
    String REFUNDING = "OT08";
    
    /** 已退款 */
    String REFUNDED = "OT09";
    
    /** 取消订单 */
    String CANCELLED = "OT10";
    
    /** 错误订单 */
    String WRONG = "OT11";
}
