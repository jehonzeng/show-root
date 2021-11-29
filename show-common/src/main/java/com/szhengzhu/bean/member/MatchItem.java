package com.szhengzhu.bean.member;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jehon
 */
@Data
public class MatchItem implements Serializable {

    private static final long serialVersionUID = 7732205059690269585L;

    private String matchId;

    private String teamId;
}
