package com.szhengzhu.bean.ordering.print;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PrintBase {
    
    /** 打印机编码 */
    private String printerCode;
    
    /** 打印类型： 1：制作单 2：预览单 3：预结账单 4：结账单*/
    private Integer printType;
    
    /** 套接字 */
    private String socket;

    /** 端口类型:1000串口，1001并口，1002USB,1003网口 */
    private Integer portType;

    /** 部门名称 */
    private String deptName;

    /** 门店小票结尾 */
    private String tail;
    
    /** 打印人*/
    private String printer;
    
    private PrintIndent indent;
    
    /** 打印商品明细区分商品 */
    private String detailId;
    
    private PrintProduce produce;
    
    private PrintIncome income;
}
