package com.szhengzhu.bean.goods;

import java.io.Serializable;

import lombok.Data;

@Data
public class CookFollow implements Serializable {

    private static final long serialVersionUID = -2226763724400104963L;

    private String userId;
    
    private String cookId;
    
    private Integer follow;
}
