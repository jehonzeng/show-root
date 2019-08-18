package com.szhengzhu.bean.goods;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 商品库存信息
 * 
 * @author Administrator
 * @date 2019年2月28日
 */
@Data
@ApiModel
public class GoodsStock implements Serializable {

    private static final long serialVersionUID = -6120143073168733893L;

    private String markId;

    private String storehouseId;
    
    private String goodsId;
    
    private String specificationIds;

    private Boolean serverStatus;

    private Integer totalStock;

    private Integer currentStock;

}