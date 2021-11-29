package com.szhengzhu.bean.member;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 */
@Data
public class Activity implements Serializable {

    private static final long serialVersionUID = 7272462996172421415L;

    private String markId;

    @NotBlank
    private String theme;

    private String code;

    /** 广告类型（0：跳转 1：充值） */
    private Integer type;

    /** 广告类型为1时，选择充值模板 */
    private String ruleId;

    private String storeId;

    private Date startTime;

    private Date stopTime;

    private String awardUrl;

    private String imagePath;

    private Date createTime;

    private Date modifyTime;

    private String creator;

    private String modifier;

    private Boolean status;
}