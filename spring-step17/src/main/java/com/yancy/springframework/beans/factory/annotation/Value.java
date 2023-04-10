package com.yancy.springframework.beans.factory.annotation;

import java.lang.annotation.*;


/**
 * 属性注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Documented
public @interface Value {

    /**
     * 属性占位符 | 真实值
     * @return
     */
    String value();
}
