package com.szhengzhu.bean.member.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author jehon
 */
@Data
public class MatchTeamVo implements Serializable {

    private static final long serialVersionUID = 8369857867503392957L;

    private String markId;

    private String teamName;

    private String imgPath;

    private String remark;

    /* true : 淘汰*/
    private Boolean weedOut = false;

    private List<StageResultVo> stageList;
}
