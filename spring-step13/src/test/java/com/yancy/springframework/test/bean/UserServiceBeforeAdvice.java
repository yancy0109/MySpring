package com.yancy.springframework.test.bean;

import com.yancy.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @author yancy0109
 */
public class UserServiceBeforeAdvice implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("拦截方法: " + method.getName());
    }
}
