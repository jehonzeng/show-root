package com.szhengzhu.bean.ordering;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PrinterLog implements Serializable{
    
    private static final long serialVersionUID = -5204116550384042621L;

    private String markId;
    
    private String tableId;
    
    private String indentId;

    private String printerCode;

    private Integer statusCode;

    private Integer printType;

    private Date sendTime;

    private String errorInfo;

    private String printData;
}