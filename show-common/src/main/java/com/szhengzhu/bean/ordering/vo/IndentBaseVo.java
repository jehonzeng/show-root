package com.szhengzhu.bean.ordering.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.szhengzhu.bean.xwechat.vo.DetailModel;

import lombok.Data;

@Data
public class IndentBaseVo implements Serializable {

    private static final long serialVersionUID = 9041073504424552892L;
    
    private String indentId;

    private String indentNo;
    
    private BigDecimal indentTotal;
    
    private String employee;
    
    private String indentStatus;
    
    private List<DetailModel> detailList;
    
}
