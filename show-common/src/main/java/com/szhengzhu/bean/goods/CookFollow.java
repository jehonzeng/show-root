package com.szhengzhu.bean.goods;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CookFollow implements Serializable {

    private static final long serialVersionUID = -2226763724400104963L;

    @NotBlank
    private String userId;

    @NotBlank
    private String cookId;

    @NotNull
    private Integer follow;
}
