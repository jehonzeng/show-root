package com.szhengzhu.bean.vo;

import com.szhengzhu.bean.goods.GoodsVoucher;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsVoucherVo extends GoodsVoucher{

    private static final long serialVersionUID = -9159617792366180955L;
    
    private String productName;
    
    private String specList;

}
