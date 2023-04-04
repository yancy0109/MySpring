package com.yancy.springframework.aop;

import java.lang.reflect.Method;

/**
 * 拦截器链路 通过 MethodInterceptor 实现
 * @author yancy0109
 */
public interface MethodBeforeAdvice extends BeforeAdvice{

    void before(Method method, Object[] args, Object target) throws Throwable;
}
