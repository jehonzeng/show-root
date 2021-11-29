package com.szhengzhu.annotation;

import java.lang.annotation.*;

/**
 * 防重复提交
 */
@Target(ElementType.METHOD) // 作用到方法上
@Retention(RetentionPolicy.RUNTIME) // 运行时有效
@Documented
@Inherited
public @interface NoRepeatSubmit {

}
