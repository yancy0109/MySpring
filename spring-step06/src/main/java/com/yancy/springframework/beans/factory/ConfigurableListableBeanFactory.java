package com.yancy.springframework.beans.factory;

import com.yancy.springframework.beans.BeansException;
import com.yancy.springframework.beans.factory.config.BeanDefinition;

public interface ConfigurableListableBeanFactory {

    BeanDefinition getBeanDefinition(String beanName);

    void preInstantiateSingletons() throws BeansException;
}
