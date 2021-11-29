package com.szhengzhu.bean.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 投票机会
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchChance implements Serializable {

    private static final long serialVersionUID = 4681528204194538723L;

    private String userId;

    private String matchId;

    private Integer totalCount;

    private Integer usedCount;

    private Date createTime;

    private Date modifyTime;
}
