package com.yancy.springframework.beans.factory.support;

import com.yancy.springframework.beans.factory.config.BeanDefinition;

/**
 * @author yancy0109
 */
public interface BeanDefinitionRegistry {

    /**
     * 注册Bean对象至注册表
     * @param beanName
     * @param beanDefinition
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /**
     * 判断Bean对象Name是否重复
     * @param beanName
     * @return
     */
    boolean containsBeanDefinition(String beanName);
}
