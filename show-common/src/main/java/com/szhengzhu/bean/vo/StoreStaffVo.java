package com.szhengzhu.bean.vo;

import com.szhengzhu.bean.user.UserInfo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StoreStaffVo extends UserInfo{

    /** */
    private static final long serialVersionUID = 8297372001089378654L;
    
    private String storeId;

    
}
