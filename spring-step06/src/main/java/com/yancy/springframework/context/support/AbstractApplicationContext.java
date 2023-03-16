package com.yancy.springframework.context.support;

import com.yancy.springframework.beans.BeansException;
import com.yancy.springframework.beans.core.io.DefaultResourceLoader;
import com.yancy.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.yancy.springframework.context.ConfigurableApplicationContext;

public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    @Override
    public void refresh() throws BeansException {
        // 建立 BeanFactory 并加载 BeanDefinition
        refreshFactory();

        // 获取 BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 在 Bean 实例化之前 执行 BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);

        // BeanPostProcessor 需要提前于其他 Bean 对象实例化之前执行注册操作
        registerBeanPostProcessors(beanFactory);

        // 提前实例化单例Bean对象
        beanFactory.preInstantiateSingletons();


    }

    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        beanFactory.getBeansOfType
    }

    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {


    }

    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    protected abstract void refreshFactory();
}
