package com.szhengzhu.bean.ordering;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 */
@Data
public class Table implements Serializable {

    private static final long serialVersionUID = 4190465892049770816L;

    private String markId;

    private String code;

    @NotBlank
    private String name;

    @NotNull
    private Integer seats;

    private String areaId;

    private String clsId;

    private String storeId;

    private String qrCode;
    
    private String qrUrl;

    private Date createTime;

    private Date modifyTime;
    
    private Integer manNum;
    
    private Date openTime;
    
    private String tempNum;

    private String tableStatus;
    
    private String status;
    
}