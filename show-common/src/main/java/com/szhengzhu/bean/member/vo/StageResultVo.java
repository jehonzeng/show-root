package com.szhengzhu.bean.member.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author jehon
 */
@Data
public class StageResultVo implements Serializable {

    private static final long serialVersionUID = -6736540764251629217L;

    private String stageId;

    private String stageName;

    private Date lastTime;

    private Integer teamStatus;

    private Integer voteCount;

    private Date stageStart;
}