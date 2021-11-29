package com.szhengzhu.bean.goods;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 商品规格属性
 *
 * @author Administrator
 * @date 2019年2月28日
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel
public class SpecificationInfo implements Serializable {

    private static final long serialVersionUID = -4230959177044182201L;

    private String markId;

    @NotBlank
    private String attrName;

    @NotBlank
    private String attrValue;

    private Boolean serverStatus;

    private Integer sort;

    @NotBlank
    private String typeId;

    private Boolean defaultOr;

}
