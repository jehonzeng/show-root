package com.szhengzhu.bean.goods;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MealContent implements Serializable{

    private static final long serialVersionUID = 6687350755719739839L;

    private String markId;

    @NotBlank
    private String mealId;

    private String content;

}