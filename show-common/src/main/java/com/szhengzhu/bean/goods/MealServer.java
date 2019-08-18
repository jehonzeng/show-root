package com.szhengzhu.bean.goods;

import java.io.Serializable;

import lombok.Data;

/**
 * 套餐服务支持
 * 
 * @author Administrator
 * @date 2019年6月20日
 */
@Data
public class MealServer implements Serializable{

    private static final long serialVersionUID = 5461803780368415688L;

    private String markId;

    private String mealId;

    private String serverId;

    private Integer sort;
}