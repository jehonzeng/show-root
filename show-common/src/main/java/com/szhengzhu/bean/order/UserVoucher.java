package com.szhengzhu.bean.order;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.szhengzhu.bean.goods.GoodsVoucher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Administrator
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserVoucher implements Serializable{
    
    private static final long serialVersionUID = 8450938923316760299L;

    private String markId;

    private String userId;

    private String voucherId;
    
    private String voucherName;

    private String productId;

    private Integer productType;

    private String specificationIds;

    private Integer orderType;

    private String orderNo;

    private Date createTime;

    private Integer quantity;

    private Date useTime;
    
    private BigDecimal salePrice;
    
    private String specs;
    
    public UserVoucher(String userId, GoodsVoucher voucher) {
        this.markId = IdUtil.getSnowflake(1,1).nextIdStr();
        this.userId = userId;
        this.voucherId = voucher.getMarkId();
        this.voucherName = voucher.getVoucherName();
        this.productId = voucher.getProductId();
        this.productType =voucher.getProductType() ;
        this.specificationIds = voucher.getSpecificationIds();
        //表示非购买获取
        this.orderType = -1;
        this.createTime = DateUtil.date();
        this.quantity = 1;
    }
}