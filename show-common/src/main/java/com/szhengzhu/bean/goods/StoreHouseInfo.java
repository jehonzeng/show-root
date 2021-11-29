package com.szhengzhu.bean.goods;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 仓库基础信息
 *
 * @author Administrator
 * @date 2019年2月28日
 */
@Data
@ApiModel
public class StoreHouseInfo implements Serializable {

    private static final long serialVersionUID = 4780025294592131814L;

    @ApiModelProperty(value = "ID", name = "markId")
    private String markId;

    @ApiModelProperty(value = "仓库名称", name = "name")
    private String name;

    @ApiModelProperty(value = "仓库联系人", name = "contact")
    private String contact;

    @ApiModelProperty(value = "联系电话", name = "phone")
    private String phone;

    @ApiModelProperty(value = "所在地区", name = "area")
    private String area;

    @ApiModelProperty(value = "所在城市", name = "city")
    private String city;

    @ApiModelProperty(value = "所在省份", name = "province")
    private String province;

    @ApiModelProperty(value = "所在国家", name = "country")
    private String country;

    @ApiModelProperty(value = "仓库详细地址", name = "address")
    private String address;

    @ApiModelProperty(value = "有效否（fasle:无效；true:有效）", name = "serverStatus",allowableValues="false,true")
    private Boolean serverStatus;

    @ApiModelProperty(value = "经度", name = "longitude")
    private Double longitude;

    @ApiModelProperty(value = "纬度", name = "latitude")
    private Double latitude;

}
