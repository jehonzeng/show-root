package com.szhengzhu.bean.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class IntegralVo implements Serializable {

    private static final long serialVersionUID = 3806454498211713399L;

    private String userId;
    
    private String nickName;
    
    private String phone;
    
    private Integer gender;
    
    // 总积分
    private Integer total;
    
    // 已使用积分总数
    private Integer used;
    
    // 剩余积分总数
    private Integer remain;
}
