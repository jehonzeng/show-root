package com.szhengzhu.bean.goods;

import java.io.Serializable;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 商品活动促销标识信息
 * 
 * @author Administrator
 * @date 2019年3月27日
 */
@Data
public class IconInfo implements Serializable {

    private static final long serialVersionUID = 5677558127360283890L;

    private String markId;

    @NotBlank
    private String theme;

    private String imagePath;

    private Boolean serverStatus;

}