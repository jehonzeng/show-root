package com.szhengzhu.bean.vo;

import com.szhengzhu.bean.goods.GoodsInfo;
import com.szhengzhu.bean.goods.SpecialItem;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SpecialGoodsVo extends SpecialItem {

    private static final long serialVersionUID = -2478036540046089852L;

    private GoodsInfo goods;
}
