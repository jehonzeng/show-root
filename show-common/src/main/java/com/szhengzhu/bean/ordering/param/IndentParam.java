package com.szhengzhu.bean.ordering.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 */
@Data
public class IndentParam implements Serializable {

    private static final long serialVersionUID = 4478287375864318305L;

    @NotBlank
    private String tableId;
    
    private String employeeId;

    @NotEmpty
    private List<DetailParam> detailList;
}
