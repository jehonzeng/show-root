package com.szhengzhu.bean.excel;

import com.szhengzhu.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

@Data
public class VoucherCodeExcel implements Serializable {

    private static final long serialVersionUID = -809154574676401191L;

    @Excel(name = "代金券码", sort = 1)
    private String code;
}
