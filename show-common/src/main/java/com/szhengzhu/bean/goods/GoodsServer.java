package com.szhengzhu.bean.goods;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品服务支持
 * 
 * @author Administrator
 * @date 2019年3月27日
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GoodsServer implements Serializable {

    private static final long serialVersionUID = 3710991775162970149L;

    private String markId;

    private String serverId;

    private String goodsId;

    private Integer sort;
}