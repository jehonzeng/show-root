package com.szhengzhu.bean.member.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jehon
 */
@Data
public class MatchVoteVo implements Serializable {

    private static final long serialVersionUID = -1887167937107588409L;

    private String teamName;

    private String imgPath;

    private String teamStatus;

    private Integer vote;

    private Integer promotion;
}
