package com.szhengzhu.bean.wechat.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class NavVo implements Serializable {
    
    private static final long serialVersionUID = -6464122159263506783L;

    private String markId;
    
    private String navCode;
    
    private String remark;
    
    private List<NavItemVo> items;
    
}
