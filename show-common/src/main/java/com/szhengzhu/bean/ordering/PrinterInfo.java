package com.szhengzhu.bean.ordering;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class PrinterInfo implements Serializable{

    private static final long serialVersionUID = 6190038634623400275L;

    private String markId;

    @NotBlank
    private String printerCode;

    @NotBlank
    private String portName;

    @NotNull
    private Integer portType;

    private int serverStatus;
    
    private Date createTime;
    
    private Date modifyTime;
    
    private String storeId;
    
    private String deptName;

    private String tail;
    
    private String remark;
    
    private Integer sort;

}