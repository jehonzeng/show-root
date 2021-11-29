package com.szhengzhu.bean.xwechat.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class TableOpenParam implements Serializable {

    private static final long serialVersionUID = -7408865529751556138L;

    @NotBlank
    private String tableId;

    private String storeId;

    @NotNull
    private Integer manNum;

    private String userId;
}
