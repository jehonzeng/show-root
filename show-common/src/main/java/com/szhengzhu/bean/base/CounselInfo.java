package com.szhengzhu.bean.base;

import java.io.Serializable;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CounselInfo implements Serializable{

    private static final long serialVersionUID = 8675676594258523316L;

    private String markId;

    @NotBlank
    private String nickName;

    private String userAdvise;

    private String email;

    private String phone;
}