package com.szhengzhu.bean.order;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TeambuyGroup implements Serializable {
    
    private static final long serialVersionUID = 2768248596576108992L;

    private String markId;

    private String groupNo;

    private String theme;

    private String goodsId;

    private String superNo;

    private String teambuyId;

    private String creator;

    private Date createTime;

    private Integer reqCount;
    
    private Integer currentCount;

    private Integer vaildTime;

    private String modifier;

    private Date modifyTime;

    private Integer type;

    private String groupStatus;
    
    private String statusDesc;
    
    private List<TeambuyOrder> items;
}