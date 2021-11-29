package com.szhengzhu.bean.ordering.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiveParam implements Serializable {

    private static final long serialVersionUID = 5207395727967638622L;

    @NotBlank
    private String templateId;

    @NotNull
    private Integer quantity;

    private String accountId;

    private String userId;
}
