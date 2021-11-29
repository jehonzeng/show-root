package com.szhengzhu.bean.member.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author jehon
 */
@Data
public class ExchangeParam implements Serializable {

    private static final long serialVersionUID = 7675606931622824805L;

    @NotBlank
    private String matchId;

    @NotBlank
    private String userId;

    @NotNull
    private Integer quantity;

    private String employeeId;
}
