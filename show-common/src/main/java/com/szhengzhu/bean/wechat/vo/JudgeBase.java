package com.szhengzhu.bean.wechat.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class JudgeBase implements Serializable {
    
    private static final long serialVersionUID = 8208401010876908285L;

    private String judgeId;
    
    private String nickName;
    
    private String headerImg;
    
    private Date addTime;
    
    private Integer star;
    
    private String description;
}
