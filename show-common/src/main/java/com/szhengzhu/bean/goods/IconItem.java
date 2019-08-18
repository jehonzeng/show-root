package com.szhengzhu.bean.goods;

import java.io.Serializable;

import lombok.Data;
@Data
public class IconItem implements Serializable{ 

    private static final long serialVersionUID = 6420989004642019718L;

    private String markId;

    private String goodsId;

    private String iconId;

}