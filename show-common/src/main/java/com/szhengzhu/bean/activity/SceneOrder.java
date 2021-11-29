package com.szhengzhu.bean.activity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SceneOrder implements Serializable {
    
    private static final long serialVersionUID = -8088473147090442352L;

    private String markId;

    private String orderNo;

    private String userId;

    private Date orderTime;

    private BigDecimal payAmount;
    
    private String orderStatus;
    
    private String nickName;
    
    private String statusDesc;
    
    private List<SceneItem> itemList;
}