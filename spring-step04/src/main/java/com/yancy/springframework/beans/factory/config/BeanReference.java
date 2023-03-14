package com.yancy.springframework.beans.factory.config;

/**
 * 对象引用类型
 * @author yancy0109
 */
public class BeanReference {

    private final String beanName;

    public BeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}
