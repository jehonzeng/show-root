package com.szhengzhu.bean.ordering.vo;

import com.szhengzhu.bean.ordering.PrinterInfo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrinterVo extends PrinterInfo{

    private static final long serialVersionUID = -2668609924348807965L;

    private String[] commodityList;
}
