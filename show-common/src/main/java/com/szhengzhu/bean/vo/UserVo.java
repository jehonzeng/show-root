package com.szhengzhu.bean.vo;

import com.szhengzhu.bean.user.UserInfo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserVo extends UserInfo {

    private static final long serialVersionUID = 2142943398910307147L;

    private String userLevelname;
    
    private String[] roleIds;
}
