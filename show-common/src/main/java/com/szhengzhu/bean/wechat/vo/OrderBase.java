package com.szhengzhu.bean.wechat.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderBase implements Serializable {

    private static final long serialVersionUID = -735725660581605956L;
    
    private String orderId;
    
    private String orderNo;
    
    private String orderStatus;
    
    private String statusDesc;
    
    private Date orderTime;
    
    private Integer count;
    
    private BigDecimal payAmount;
    
    private List<String> imagePath;

    /** 1:普通订单 2：团购订单  3：秒杀订单 4：现场订单 */
    private String type;
    
    private String userId;

}
