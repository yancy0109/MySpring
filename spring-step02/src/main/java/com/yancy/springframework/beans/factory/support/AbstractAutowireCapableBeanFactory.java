package com.yancy.springframework.beans.factory.support;

import com.yancy.springframework.beans.factory.BeansException;
import com.yancy.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.InvocationTargetException;

/**
 *
 * 实现createBean方法
 * @author yancy0109
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory{
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        Object bean;
        try {
            // 通过反射创建对象
            bean = beanDefinition.getBeanClass().getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new BeansException("instantiation of bean failed", e);
        }
        super.addSingleton(beanName, bean);
        return bean;
    }
}
