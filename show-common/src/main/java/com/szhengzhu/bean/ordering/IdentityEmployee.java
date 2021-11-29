package com.szhengzhu.bean.ordering;

import java.io.Serializable;

import lombok.Data;

@Data
public class IdentityEmployee implements Serializable {

    private static final long serialVersionUID = 984048500568697626L;

    private String employeeId;

    private String identityId;
}