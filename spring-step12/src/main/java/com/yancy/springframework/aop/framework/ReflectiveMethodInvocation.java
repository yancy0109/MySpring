package com.yancy.springframework.aop.framework;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

/**
 * @author yancy0109
 */
public class ReflectiveMethodInvocation implements MethodInvocation {

    // 目标参数
    protected final Object target;

    // 方法
    protected final Method method;

    // 入参
    protected final Object[] arguments;

    public ReflectiveMethodInvocation(Object target, Method method, Object[] args) {
        this.target = target;
        this.method = method;
        this.arguments = args;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Object[] getArguments() {
        return arguments;
    }

    /**
     * 根据 属性参数 反射调用 原对象 方法
     * @return
     * @throws Throwable
     */
    @Override
    public Object proceed() throws Throwable {
        return method.invoke(target, arguments);
    }

    @Override
    public Object getThis() {
        return target;
    }

    @Override
    public AccessibleObject getStaticPart() {
        return method;
    }
}
