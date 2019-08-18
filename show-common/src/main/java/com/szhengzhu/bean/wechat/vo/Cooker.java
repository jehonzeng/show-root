package com.szhengzhu.bean.wechat.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class Cooker implements Serializable {

    private static final long serialVersionUID = 6838680072452727715L;

    private String cookerId;
    
    private String shortName;
    
    private String imagePath;
    
    private Integer follow;
    
    private Integer fans;
    
    private String personalSignature;
    
    private String description;
    
    List<GoodsBase> goodsBases;
}
