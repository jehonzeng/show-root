package com.szhengzhu.bean.goods;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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

    @ApiModelProperty(value = "ID", name = "markId")
    private String markId;

    @ApiModelProperty(value = "中文名称", name = "cnName")
    private String cnName;

    @ApiModelProperty(value = "英文名称", name = "enName")
    private String enName;

    @ApiModelProperty(value = "品牌有效否（fasle:无效；true:有效）", name = "brandStatus", allowableValues = "false,true")
    private Boolean brandStatus;

    @ApiModelProperty(value = "品牌描述", name = "description")
    private String description;

    @ApiModelProperty(value = "品牌官方网址", name = "brandUrl")
    private String brandUrl;

    @ApiModelProperty(hidden = true ,value = "关联品牌LOGO图片", name = "brandLogo")
    private String brandLogo;

    @ApiModelProperty(value = "品牌创建时间", name = "regTime")
    private Date regTime;

    @ApiModelProperty(value = "排序", name = "sort")
    private Integer sort;
    
    private String ownerName;//称呼
    
    private String serverType;//合作类型
    
    private String cooperateType;//冗余
    
    private String phone;//联系方式

}