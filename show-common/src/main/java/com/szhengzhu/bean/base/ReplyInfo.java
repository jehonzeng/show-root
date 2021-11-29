package com.szhengzhu.bean.base;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ReplyInfo implements Serializable {

    private static final long serialVersionUID = 2907234605715899234L;

    private String markId;

    private String msgInfo;

    private String actionCode;

    private Boolean serverStatus;

    private Date startTime;

    private Date endTime;
}
