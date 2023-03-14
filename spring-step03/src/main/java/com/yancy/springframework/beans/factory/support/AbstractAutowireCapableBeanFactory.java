package com.yancy.springframework.beans.factory.support;

import com.yancy.springframework.beans.BeansException;
import com.yancy.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 *
 * 实现createBean方法
 * @author yancy0109
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory{

    private final InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException {
        Object bean;
        try {
            // 通过策略类创建对象
            bean = createBeanInstance(beanDefinition, beanName, args);
        } catch (BeansException e) {
            throw new BeansException("instantiation of bean failed", e);
        }
        super.addSingleton(beanName, bean);
        return bean;
    }

    protected Object createBeanInstance(BeanDefinition beanDefinition, String beanName, Object[] args) {
        Constructor<?> constructorForUse = null;
        Class<?> beanClass = beanDefinition.getBeanClass();
        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
        for (Constructor<?> declaredConstructor : declaredConstructors) {
            if (args != null && declaredConstructor.getParameterTypes().length == args.length) {
                // 找到符合传参的构造器 还可以细化比较参数类型
                constructorForUse = declaredConstructor;
                break;
            }
        }
        return getInstantiationStrategy().instantiate(beanDefinition, beanName, constructorForUse, args);
    }

    /**
     * 获取实例化策略对象
     * @return  实例化策略对象
     */
    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }
}
