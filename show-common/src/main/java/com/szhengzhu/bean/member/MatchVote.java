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
public class MatchVote implements Serializable {

    private static final long serialVersionUID = -615844454401950299L;

    private String markId;

    private String userId;

    private String teamId;

    private Integer quantity;

    private Date createTime;

    private String stageId;
}