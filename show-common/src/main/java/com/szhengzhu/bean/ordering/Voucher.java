package com.szhengzhu.bean.ordering;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Voucher implements Serializable {

    private static final long serialVersionUID = 5472344185378693856L;

    private String markId;

    @NotBlank
    private String name;

    @NotNull
    @Min(1)
    private Integer quantity;

    private Integer amount;

    private String storeId;

    private Date createTime;

    private Date modifyTime;

    private Date startTime;

    private Date stopTime;

    /** 扫码返回 */
    private String code;

    private Integer balance;
}
