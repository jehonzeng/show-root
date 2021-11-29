package com.szhengzhu.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
public class EncryUser implements Serializable {

    private static final long serialVersionUID = -5523633980407030567L;

    private String encryptedData;
    
    private String iv;
    
    private String code;

    private String key;
}
