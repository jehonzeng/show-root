package com.szhengzhu.bean.wechat.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class Label implements Serializable {

    private static final long serialVersionUID = -2085085685731789486L;

    private String labelId;
    
    private String theme;
    
    private String imagePath;
    
    private Integer type;
    
    private List<GoodsBase> goodsList;
}
