package com.yancy.springframework.aop;

/**
 * 切入点接口
 * 获取 ClassFilter MethodMatcher（由切点表达式提供）
 */
public interface Pointcut {


    ClassFilter getClassFilter();

    MethodMatcher getMethodMatcher();
}
