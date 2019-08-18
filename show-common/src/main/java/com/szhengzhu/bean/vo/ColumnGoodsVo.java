package com.szhengzhu.bean.vo;

import com.szhengzhu.bean.goods.ColumnGoods;
import com.szhengzhu.bean.goods.GoodsInfo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class ColumnGoodsVo extends ColumnGoods{

    private static final long serialVersionUID = -610223365714969014L;
    
    private GoodsInfo goods;
}
