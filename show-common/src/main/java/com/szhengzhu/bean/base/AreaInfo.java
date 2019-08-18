package com.szhengzhu.bean.base;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class AreaInfo implements  Serializable{
    
    private static final long serialVersionUID = 6124244390674770145L;

    private String num;

    private String name;

    private String superId;
    
    private List<AreaInfo> areas;

}