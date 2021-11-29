package com.szhengzhu.bean.base;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ScanReply implements Serializable {
    
    private static final long serialVersionUID = 5811027587793381842L;

    private String markId;

    @NotBlank
    private String scanCode;
    
    private String codeUrl;

    private String description;

    private Integer serverType;

    private String content;

    private String templateMark;

    private String imagePath;

    private String title;

    private String linkUrl;
    
    private Date startTime;

    private Date stopTime;

    private Boolean serverStatus;
    
    private Integer sort;
}