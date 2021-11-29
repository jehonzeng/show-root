package com.szhengzhu.bean.activity;

import java.io.Serializable;

import lombok.Data;

@Data
public class ScanWinner implements Serializable {

    private static final long serialVersionUID = 4624031561289401553L;

    private String winId;

    private String openId;
}