package com.yancy.springframework.context.support;

import com.yancy.springframework.beans.BeansException;
import com.yancy.springframework.beans.core.io.DefaultResourceLoader;
import com.yancy.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.yancy.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.yancy.springframework.beans.factory.config.BeanPostProcessor;
import com.yancy.springframework.context.ConfigurableApplicationContext;

import java.util.Map;

public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    @Override
    public void refresh() throws BeansException {
        // 建立 BeanFactory 并加载 BeanDefinition
        refreshBeanFactory();

        // 获取 BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 在 Bean 实例化之前 执行 BeanFactoryPostProcessor (对BeanDefinition修改)
        invokeBeanFactoryPostProcessors(beanFactory);

        // BeanPostProcessor 需要提前于其他 Bean 对象实例化之前执行注册操作
        registerBeanPostProcessors(beanFactory);

        // 提前实例化单例Bean对象
        beanFactory.preInstantiateSingletons();


    }

    protected abstract void refreshBeanFactory() throws BeansException;

    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }

    }

    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }

    }

    protected abstract ConfigurableListableBeanFactory getBeanFactory();

}
