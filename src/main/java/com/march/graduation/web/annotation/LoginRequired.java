package com.march.graduation.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginRequired {

    boolean checkCsrfToken() default false;

    boolean needBindMobile() default false;

    /**
     * 白名单帐号编辑游记，不需要手机号
     * 
     * @return
     */
    boolean filterWhiteList() default false;
}