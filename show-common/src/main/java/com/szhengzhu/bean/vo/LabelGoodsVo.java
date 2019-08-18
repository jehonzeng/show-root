package com.szhengzhu.bean.vo;

import com.szhengzhu.bean.goods.GoodsInfo;
import com.szhengzhu.bean.goods.LabelGoods;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class LabelGoodsVo extends LabelGoods {

    private static final long serialVersionUID = -6104888955339595544L;
    
    private GoodsInfo goods;

}
