package com.yancy.springframework.beans.factory.support;

import com.yancy.springframework.beans.BeansException;
import com.yancy.springframework.beans.factory.BeanFactory;
import com.yancy.springframework.beans.factory.config.BeanDefinition;
import com.yancy.springframework.beans.factory.config.DefaultSingletonBeanRegistry;

/**
 * 抽象Bean工厂
 * 模板方法
 * @author yancy0109
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    @Override
    public Object getBean(String beanName) throws BeansException {
        return doGetBean(beanName, null);
    }

    @Override
    public <T> T getBean(String beanName, Class<?> requiredType) {
        return (T) getBean(beanName);
    }

    @Override
    public Object getBean(String beanName, Object... args) throws BeansException {
        return doGetBean(beanName, args);
    }

    protected <T> T doGetBean(final String beanName, Object[] args) {
        Object singletonBean = super.getSingleton(beanName);
        if (singletonBean != null) {
            return (T) singletonBean;
        }
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        return (T) this.createBean(beanName, beanDefinition, args);
    }

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException;
}
