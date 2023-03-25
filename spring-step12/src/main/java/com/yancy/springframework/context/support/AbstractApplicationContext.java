package com.yancy.springframework.context.support;

import com.yancy.springframework.beans.BeansException;
import com.yancy.springframework.beans.core.io.DefaultResourceLoader;
import com.yancy.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.yancy.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.yancy.springframework.beans.factory.config.BeanPostProcessor;
import com.yancy.springframework.context.ApplicationEvent;
import com.yancy.springframework.context.ApplicationListener;
import com.yancy.springframework.context.ConfigurableApplicationContext;
import com.yancy.springframework.context.event.*;

import java.util.Collection;
import java.util.Map;

/**
 * 抽象Application
 * 模版方法模式 实现refresh方法
 * 将自方法进行抽象定义，交由子类实现
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    private ApplicationEventMulticaster applicationEventMulticaster;

    /**
     * 模版方法模式
     * @throws BeansException
     */
    @Override
    public void refresh() throws BeansException {
        // 建立 BeanFactory 并加载 BeanDefinition
        refreshBeanFactory();

        // 获取 BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 添加 ApplicationContextAwareProcessor 让继承自 ApplicationContextAware 的 Bean 对象都能感知到所属的 ApplicationContext
        beanFactory.addBeanPostProcessor(new ApplicationAwareProcessor(this));

        // 在 Bean 实例化之前 执行 BeanFactoryPostProcessor (对BeanDefinition修改)
        invokeBeanFactoryPostProcessors(beanFactory);

        // BeanPostProcessor 需要提前于其他 Bean 对象实例化之前执行注册操作
        registerBeanPostProcessors(beanFactory);

        // 初始化事件发布者
        initApplicationEventMulticaster();

        // 注册事件监听器
        registerListeners();

        // 提前实例化单例Bean对象
        beanFactory.preInstantiateSingletons();

        // 发布容器刷新完成事件
        finishRefresh();
    }

    private void finishRefresh() {
        publishEvent(new ContextRefreshedEvent(this));
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        applicationEventMulticaster.multicastEvent(event);
    }

    private void registerListeners() {
        Collection<ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class).values();
        for (ApplicationListener listener : applicationListeners) {
            applicationEventMulticaster.addApplicationListener(listener);
        }

    }

    private void initApplicationEventMulticaster() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
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


    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(
                new Thread(this::close)
        );
    }

    @Override
    public void close() {
        // 发布容器关闭事件
        publishEvent(new ContextClosedEvent(this));

        // 执行销毁单例Bean的销毁方法
        getBeanFactory().destroySingletons();
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        return getBeanFactory().getBean(beanName);
    }

    @Override
    public <T> T getBean(String beanName, Class<T> requiredType) {
        return getBeanFactory().getBean(beanName, requiredType);
    }

    @Override
    public Object getBean(String beanName, Object... args) throws BeansException {
        return getBeanFactory().getBean(beanName, args);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }
}
