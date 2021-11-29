package com.szhengzhu.bean.ordering;

import com.szhengzhu.bean.ordering.print.PrintPay;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncomeByType implements Serializable {
    private static final long serialVersionUID = 9025302860820372722L;
    /**
     *支付类型ID
     */
    private String typeId;
    /**
     *支付类型名称
     */
    private String type;
    /**
     *支付类型编码
     */
    private String payCode;
    /**
     *支付类型描述
     */
    private String description;
    /**
     *支付信息
     */
    private List<PrintPay> list;
}
