package com.szhengzhu.core;

import java.math.BigDecimal;
import java.util.HashMap;

public class CountMap extends HashMap<String, BigDecimal>{

    private static final long serialVersionUID = -2425376385551770666L;

   //把key相同的数值累加
    public BigDecimal put(String key, BigDecimal value) {
        return super.put(key, get(key).add(value));
    }

    //重新定义通过key获取值的方法
    public BigDecimal get(String key) {
        return super.get(key) == null ? new BigDecimal(0) : super.get(key);
    }
}
