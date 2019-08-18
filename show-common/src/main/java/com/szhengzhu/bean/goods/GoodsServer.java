package com.szhengzhu.bean.goods;

import java.io.Serializable;

import lombok.Data;

/**
 * 商品服务支持
 * 
 * @author Administrator
 * @date 2019年3月27日
 */
@Data
public class GoodsServer implements Serializable {

    private static final long serialVersionUID = 3710991775162970149L;

    private String markId;

    private String serverId;

    private String goodsId;

    private Integer sort;
}