package com.szhengzhu.bean.base;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductContent implements Serializable{

    private static final long serialVersionUID = -1786814140498680312L;

    private String markId;

    @NotBlank
    private String productId;

    private String content;
}