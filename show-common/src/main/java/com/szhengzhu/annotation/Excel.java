package com.szhengzhu.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.TYPE })
public @interface Excel {

    // 列名称
    String name() default "";

    // 忽略该字段默认没有忽略
    boolean skip() default false;

    // 序列化格式
    String format() default "";

    // 顺序
    int sort() default 20;

    // 判断是价格参数
    boolean price() default false;

}
