package com.yancy.springframework.beans.factory.config;

import com.yancy.springframework.beans.BeansException;

/**
 * @author yancy0109
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor{

    /**
     * 对象执行初始化方法之前执行
     * @param beanClass
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;
}

