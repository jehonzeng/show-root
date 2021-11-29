package com.szhengzhu.bean.member.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * @author jehon
 */
@Data
public class MatchVoteParam implements Serializable {

    private static final long serialVersionUID = 4069945700293149906L;

    @NotBlank
    private String matchId;

    private String userId;

    private List<MatchTeamParam> teams;
}
