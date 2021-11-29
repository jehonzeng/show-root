package com.szhengzhu.bean.member;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MatchTicket implements Serializable {

    private static final long serialVersionUID = 8657032040614782823L;

    private String markId;

    private String matchId;

    private String templateId;

    private Integer quantity;

    private Date createTime;
}