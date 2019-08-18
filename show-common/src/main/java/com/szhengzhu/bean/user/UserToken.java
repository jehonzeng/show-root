package com.szhengzhu.bean.user;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class UserToken implements Serializable {

    private static final long serialVersionUID = -7990356228790681394L;

    private String markId;

    private String userId;

    private Date refreshTime;

    private String token;
}