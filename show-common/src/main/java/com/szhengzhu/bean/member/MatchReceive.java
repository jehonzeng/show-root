package com.szhengzhu.bean.member;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MatchReceive implements Serializable {

    private static final long serialVersionUID = 8502481507833044517L;

    private String markId;

    private String userId;

    private String prizeId;

    private Integer quantity;

    private Date createTime;
}