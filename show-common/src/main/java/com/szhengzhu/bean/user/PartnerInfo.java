package com.szhengzhu.bean.user;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 合作商信息表
 * 
 * @author Administrator
 * @date 2019年6月12日
 */
@Data
public class PartnerInfo implements Serializable{
    
    private static final long serialVersionUID = 7258919428541629093L;

    private String markId;

    private String identificationCode;

    private String name;

    private Date addTime;

    private String phone;

    private String address;
}