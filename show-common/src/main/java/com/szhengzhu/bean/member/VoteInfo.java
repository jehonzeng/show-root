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
public class VoteInfo implements Serializable {
    private static final long serialVersionUID = 7832628484416763864L;

    private String userId;

    private String phone;

    private String teamName;

    private String stageName;

    private Integer quantity;

    private Date voteTime;
}
