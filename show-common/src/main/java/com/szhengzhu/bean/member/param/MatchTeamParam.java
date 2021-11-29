package com.szhengzhu.bean.member.param;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jehon
 */
@Data
public class MatchTeamParam implements Serializable {

    private static final long serialVersionUID = 5996945447401589963L;

    private String teamId;

    private Integer quantity;
}
