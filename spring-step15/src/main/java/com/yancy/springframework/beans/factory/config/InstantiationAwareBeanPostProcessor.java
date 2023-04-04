package com.yancy.springframework.beans.factory.config;

import com.yancy.springframework.beans.BeansException;
import com.yancy.springframework.beans.PropertyValues;

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

    /**
     * 在 Bean 对象实例化完成后，设置属性操作之前执行此方法
     * @param pvs
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException;
}

