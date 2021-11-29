package com.szhengzhu.bean.vo;

import java.io.Serializable;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CustomerMessage implements Serializable{
    
    /** */
    private static final long serialVersionUID = 112168158953443727L;

    private String openId;
    
    private String title;
    
    private String content;
    
    private String imagePath;
    
    private String aticleImage;
    
    private String description;
    
    private String linkUrl;

    @NotNull
    private Integer msgType;

}
