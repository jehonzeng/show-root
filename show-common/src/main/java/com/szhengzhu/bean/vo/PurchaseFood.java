package com.szhengzhu.bean.vo;

import com.szhengzhu.bean.goods.FoodsInfo;
import com.szhengzhu.bean.goods.PurchaseInfo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseFood extends PurchaseInfo {

    private static final long serialVersionUID = 7406889318407174204L;
    
    private FoodsInfo food;

}
