package com.szhengzhu.bean.ordering.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
public class DetailDiscountParam implements Serializable {

    private static final long serialVersionUID = -3955273156581507849L;

    @NotBlank
    private String indentId;

    private String employeeId;

    @NotBlank
    private String discountId;

    @NotEmpty
    private List<String> detailIds;
}
