package com.szhengzhu.bean.vo;

import com.szhengzhu.bean.goods.FoodsInfo;
import com.szhengzhu.bean.goods.PurchaseHistory;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PurchaseHistoryVo extends PurchaseHistory{

    private static final long serialVersionUID = -893648774688068681L;
    
    private FoodsInfo food;

}
