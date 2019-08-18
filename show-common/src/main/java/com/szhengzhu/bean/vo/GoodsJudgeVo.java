package com.szhengzhu.bean.vo;

import com.szhengzhu.bean.goods.GoodsJudge;
import com.szhengzhu.bean.order.OrderDelivery;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GoodsJudgeVo extends GoodsJudge {

    private static final long serialVersionUID = 1945277499780755531L;
    
    private String goodsName;//商品名称
    
    private OrderDelivery delivery;

}
