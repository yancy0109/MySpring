package com.yancy.springframework.context.support;

import com.yancy.springframework.beans.BeansException;
import com.yancy.springframework.beans.factory.ApplicationContextAware;
import com.yancy.springframework.beans.factory.config.BeanPostProcessor;
import com.yancy.springframework.context.ApplicationContext;

/**
 * 包装处理器
 * ApplicationContext 获取不能在创建Bean过程拿到，需要在 refresh 时，将 ApplicationContext 写入 BeanProcessor，
 * 再由 postProcessBeforeInitialization 调用
 * @author yancy0109
 */
public class ApplicationAwareProcessor implements BeanPostProcessor {

    private final ApplicationContext applicationContext;

    public ApplicationAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
