package com.szhengzhu.bean.goods;

import java.io.Serializable;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 栏目信息
 *
 * @author Administrator
 * @date 2019年3月27日
 */
@Data
public class ColumnInfo implements Serializable {

    private static final long serialVersionUID = 1325630067498788934L;

    private String markId;

    @NotBlank
    private String theme;

    private Boolean serverStatus;

    private Integer serverType;

    private String imagePath;

    private Integer sort;
}
