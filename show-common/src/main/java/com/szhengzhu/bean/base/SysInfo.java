package com.szhengzhu.bean.base;

import java.io.Serializable;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SysInfo implements Serializable {
    
    private static final long serialVersionUID = 5324322695112125267L;

    @NotBlank
    private String name;

    @NotBlank
    private String dataJson;
}