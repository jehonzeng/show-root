package com.szhengzhu.bean.wechat;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class WechatButton implements Serializable {
    
    private static final long serialVersionUID = -2500489908002843268L;

    private String name;
    
    private Integer type;
    
    private String memo;
    
    private List<WechatButton> list;
}
