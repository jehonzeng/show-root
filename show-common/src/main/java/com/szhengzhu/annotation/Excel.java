package com.szhengzhu.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.TYPE })
@Documented
@Inherited
public @interface Excel {

    // 列名称
    String name() default "";

    // 忽略该字段默认没有忽略
    boolean skip() default false;

    // 序列化格式
    String format() default "";

    //字段值显示顺序(从左到右)
    int sort() default 20;

    //是否选择下拉
    boolean select() default false;
}
