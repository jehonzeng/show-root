package com.szhengzhu.bean.user;

import java.io.Serializable;

import lombok.Data;

/**
 * 用户角色信息
 * 
 * @author Administrator
 * @date 2019年2月19日
 */
@Data
public class RoleInfo implements Serializable {
    
    private static final long serialVersionUID = 1383786527010561812L;

    private String markId;

    private String roleName;

    private String description;

    private Boolean serverStatus;
    
    private String roleCode;
}