package com.szhengzhu.bean.xwechat.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class IndentTimeModel implements Serializable {

    private static final long serialVersionUID = -6624648462000778325L;

    private String timeCode;
    
    /** true: 用户下单  false:服务员下单 */
    private Boolean userIndent;
    
    private List<DetailModel> detailList;
}
