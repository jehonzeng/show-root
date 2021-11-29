package com.szhengzhu.bean.wechat.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderModel implements Serializable {

    private static final long serialVersionUID = 1377448534139329699L;
    
    private String userId;

    @NotBlank
    private String addressId;
    
    private String couponId;
    
    private Date deliveryDate;
    
    private String remark;
    
    private String orderSource;
    
    private String code;

    /* 菜品券 */
    private List<VoucherModel> voucher;

    @NotEmpty
    private List<OrderItemModel> item;

    /* 附属品 */
    private List<AccessoryModel> accessory;

    /* 加价购商品 */
    private List<IncreaseModel> increase;
}
