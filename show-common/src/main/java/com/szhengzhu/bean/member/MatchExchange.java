package com.szhengzhu.bean.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchExchange implements Serializable {

    private static final long serialVersionUID = -5366807424056818284L;

    private String markId;

    private String matchId;

    private String userId;

    private Integer exchangeTotal;

    private Integer exchanged;

    private Date createTime;

    private Date modifyTime;
}
