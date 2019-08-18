package com.szhengzhu.bean.goods;

import java.io.Serializable;

import lombok.Data;

@Data
public class MealContent implements Serializable{

    private static final long serialVersionUID = 6687350755719739839L;

    private String markId;

    private String mealId;

    private String content;

}