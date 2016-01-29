package com.march.graduation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//********************************************
// * @author: xiaohui.shu
// * @version: 日期: 16-1-26 时间: 下午5:32
//********************************************

@Target({ ElementType.PARAMETER, ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyName {
    String value() default "";
}
