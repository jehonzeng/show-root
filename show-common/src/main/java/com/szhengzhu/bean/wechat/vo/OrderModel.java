package com.szhengzhu.bean.wechat.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class OrderModel implements Serializable {

    private static final long serialVersionUID = 1377448534139329699L;
    
    private String userId;

    private String addressId;
    
    private String couponId;
    
    private Date delvieryDate;
    
    private String remark;
    
    private String orderSource;
    
    private List<VoucherModel> voucher;
    
    private List<OrderItemModel> item;
    
    private List<AccessoryModel> accessory;
    
    private List<IncreaseModel> increase;
}
