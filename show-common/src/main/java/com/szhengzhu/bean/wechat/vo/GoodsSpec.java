package com.szhengzhu.bean.wechat.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class GoodsSpec implements Serializable {

    private static final long serialVersionUID = 701919262854664980L;
    
    private String type;
    
    private List<GoodsSpec> specList;
}
