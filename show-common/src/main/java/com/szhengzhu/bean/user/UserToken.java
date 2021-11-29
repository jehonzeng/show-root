package com.szhengzhu.bean.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserToken implements Serializable {

    private static final long serialVersionUID = -7990356228790681394L;

    private String markId;

    private String userId;

    private Date refreshTime;

    private String token;
}