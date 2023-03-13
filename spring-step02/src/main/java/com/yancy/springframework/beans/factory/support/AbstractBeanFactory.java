package com.yancy.springframework.beans.factory.support;

import com.yancy.springframework.beans.factory.BeanFactory;
import com.yancy.springframework.beans.factory.BeansException;
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
        Object singletonBean = super.getSingleton(beanName);
        if (singletonBean != null) {
            return singletonBean;
        }

        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        return this.createBean(beanName, beanDefinition);
    }

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException;
}
