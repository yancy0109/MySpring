package com.yancy.springframework.beans.factory.config;

import com.yancy.springframework.beans.factory.HierarchicalBeanFactory;

/**
 * @author yancy0109
 */
public interface ConfigurableBeanFactory extends SingletonBeanRegistry, HierarchicalBeanFactory {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
