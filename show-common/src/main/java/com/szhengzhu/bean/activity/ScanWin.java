package com.szhengzhu.bean.activity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ScanWin implements Serializable {
    
    private static final long serialVersionUID = 707830591713143065L;

    private String markId;

    @NotBlank
    private String theme;

    private Date startTime;

    private Date stopTime;

    @NotNull
    private Integer winnerNum;

    private Integer levelNum;

    private Integer winnerTotal;

    private Integer productType;

    private String productId;

    private String successMsg;

    private String failMsg;

    private String scanCode;

    private Boolean serverStatus;
    
    private String productName;
}