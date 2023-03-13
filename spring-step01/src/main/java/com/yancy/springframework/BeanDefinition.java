package com.yancy.springframework;

/**
 * BeanDefinition
 * 定义Bean实例化信息
 */
public class BeanDefinition {

    private Object bean;

    public BeanDefinition(Object bean) {
        this.bean = bean;
    }

    public Object getBean() {
        return bean;
    }
}
