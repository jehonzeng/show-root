package com.szhengzhu.bean.user;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserRole implements Serializable {

    private static final long serialVersionUID = -5545316971979198834L;

    private String roleId;

    private String userId;
}