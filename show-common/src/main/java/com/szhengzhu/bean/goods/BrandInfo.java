package com.szhengzhu.bean.goods;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 商品品牌信息
 *
 * @author Administrator
 * @date 2019年2月27日
 */
@Data
@ApiModel
public class BrandInfo implements Serializable {

    private static final long serialVersionUID = -3003205710594159236L;

    private String markId;

    @NotBlank
    private String cnName;

    private String enName;

    private Boolean brandStatus;

    private String description;

    private String brandUrl;

    private String brandLogo;

    private Date regTime;

    private Integer sort;

    /** 称呼 */
    private String ownerName;

    /** 合作类型 */
    private String serverType;

    /** 冗余字段 */
    private String cooperateTypeDesc;

    /** 联系方式 */
    private String phone;

}
