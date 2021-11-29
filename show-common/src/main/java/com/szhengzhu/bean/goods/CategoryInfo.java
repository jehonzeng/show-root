package com.szhengzhu.bean.goods;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 商品分类信息
 *
 * @author Administrator
 * @date 2019年2月27日
 */
@Data
@ApiModel
public class CategoryInfo implements Serializable {

    private static final long serialVersionUID = -7906721337145237983L;

    @ApiModelProperty(value = "ID", name = "markId")
    private String markId;

    @NotBlank
    @ApiModelProperty(value = "上级类", name = "superId")
    private String superId;

    @NotBlank
    @ApiModelProperty(value = "类别名称", name = "name")
    private String name;

    @ApiModelProperty(value = "类级别", name = "level")
    private Integer level;

    @ApiModelProperty(value = "有效否（fasle:无效；true:有效）", name = "serverStatus", allowableValues = "false,true")
    private Boolean serverStatus;

    @ApiModelProperty(hidden = true)
    private Integer sort;

    private String superName;


}
