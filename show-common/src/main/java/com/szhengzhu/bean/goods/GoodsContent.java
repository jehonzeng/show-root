package com.szhengzhu.bean.goods;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 商品文章图片详情
 * 
 * @author Administrator
 * @date 2019年2月28日
 */
@Data
@ApiModel
public class GoodsContent implements Serializable {

    private static final long serialVersionUID = 8289717235688536311L;

    private String markId;

    private String goodsId;

    private String content;
}