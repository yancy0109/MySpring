package com.yancy.springframework.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * 指定 Bean 标识注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Inherited
@Documented
public @interface Qualifier {

    String value() default "";
}
