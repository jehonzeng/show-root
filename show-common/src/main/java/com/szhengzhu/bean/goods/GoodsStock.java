package com.szhengzhu.bean.goods;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 商品库存信息
 *
 * @author Administrator
 * @date 2019年2月28日
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel
public class GoodsStock implements Serializable {

    private static final long serialVersionUID = -6120143073168733893L;

    private String markId;

    @NotBlank
    private String storehouseId;

    @NotBlank
    private String goodsId;

    private String specificationIds;

    private Boolean serverStatus;

    private Integer totalStock;

    private Integer currentStock;

}
