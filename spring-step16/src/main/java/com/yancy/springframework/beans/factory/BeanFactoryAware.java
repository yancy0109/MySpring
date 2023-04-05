package com.yancy.springframework.beans.factory;

import com.yancy.springframework.beans.BeansException;

/**
 * 容器感知类：感知到所属的BeanFactory
 * @author yancy0109
 */
public interface BeanFactoryAware extends Aware{

    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
