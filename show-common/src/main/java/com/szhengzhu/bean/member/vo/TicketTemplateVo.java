package com.szhengzhu.bean.member.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class TicketTemplateVo implements Serializable{

    private static final long serialVersionUID = -3096533424483485175L;

    private String templateId;
    
    //數量
    private Integer quantity;

}
