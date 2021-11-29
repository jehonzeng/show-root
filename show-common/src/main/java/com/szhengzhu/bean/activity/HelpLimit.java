package com.szhengzhu.bean.activity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
public class HelpLimit implements Serializable{
    
    private static final long serialVersionUID = 7486584455607133120L;

    private String markId;

    @NotBlank
    private String activityId;

    private Integer maxPoint;

    private Integer minPoint;

    private Integer limitPoint;

    private Integer priority;
    
    private String actName;

}