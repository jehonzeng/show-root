package com.szhengzhu.bean.goods;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 商品评价表
 * @author Administrator
 *
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel
public class GoodsJudge implements Serializable {

    private static final long serialVersionUID = 3893335743552812872L;

    private String markId;
    
    private String orderId;

    @NotBlank
    private String goodsId;
    
    private String specificationIds;

    private String userId;
    
    private Date addTime;

    private Boolean serverStatus;

    private String description;

    private String commentator;

    private Integer star;
    
    private Integer sort;
    
    private String goodsName;
}