package com.szhengzhu.bean.goods;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 商品文章图片详情
 * 
 * @author Administrator
 * @date 2019年2月28日
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GoodsContent implements Serializable {

    private static final long serialVersionUID = 8289717235688536311L;

    private String markId;

    @NotBlank
    private String goodsId;

    private String content;
}