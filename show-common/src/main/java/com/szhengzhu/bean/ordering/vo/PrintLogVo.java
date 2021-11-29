package com.szhengzhu.bean.ordering.vo;

import com.szhengzhu.bean.ordering.print.PrinterCode;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PrintLogVo implements Serializable {

    private static final long serialVersionUID = 5806567661454428726L;

    private String logId;
    
    private String tableCode;
    
    private String statusCode;
    
    private Integer printType;
    
    private String errorInfo;
    
    private String deptName;
    
    private String printName;
    
    private Date sendTime;
    
    private String typeName;
    
    private String printerCode;

    public void setPrinterCode(String printerCode) {
        this.printerCode = printerCode;
        this.typeName = PrinterCode.getTypeName(printerCode);
    }
}
