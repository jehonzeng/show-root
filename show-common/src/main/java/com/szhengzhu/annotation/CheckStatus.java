package com.szhengzhu.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})//注解类型，级别
@Retention(RetentionPolicy.RUNTIME)//运行时注解
@Documented
@Inherited
public @interface CheckStatus {

}
