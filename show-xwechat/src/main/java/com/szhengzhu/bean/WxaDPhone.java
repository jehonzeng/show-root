package com.szhengzhu.bean;

import lombok.Data;
import weixin.popular.bean.wxa.Watermark;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
public class WxaDPhone implements Serializable {

    private static final long serialVersionUID = -8138579582129806369L;

    private String phoneNumber;

    private String purePhoneNumber;

    private String countryCode;

    private Watermark watermark;

}
