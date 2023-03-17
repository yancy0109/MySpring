package com.yancy.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import com.yancy.springframework.beans.BeansException;
import com.yancy.springframework.beans.PropertyValue;
import com.yancy.springframework.beans.PropertyValues;
import com.yancy.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.yancy.springframework.beans.factory.config.BeanDefinition;
import com.yancy.springframework.beans.factory.config.BeanPostProcessor;
import com.yancy.springframework.beans.factory.config.BeanReference;

import java.lang.reflect.Constructor;


/**
 *
 * 实现createBean方法
 * @author yancy0109
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException {
        Object bean;
        try {
            // 通过策略类创建对象
            bean = createBeanInstance(beanDefinition, beanName, args);
            applyPropertyValues(beanName, bean, beanDefinition);
            // 给 Bean 的初始化方法 和 BeanPostProcessor 前置与后置处理方法
            bean = initializeBean(beanName, bean, beanDefinition);
        } catch (BeansException e) {
            throw new BeansException("instantiation of bean failed", e);
        }
        super.registerSingleton(beanName, bean);
        return bean;
    }

    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        // 执行 BeanPostProcessor Before 处理
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

        // 待完成
        invokeInitMethods(beanName, wrappedBean, beanDefinition);

        // 执行 BeanPostProcessor After 处理
        wrappedBean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        return wrappedBean;
    }

    private void invokeInitMethods(String beanName, Object wrappedBean, BeanDefinition beanDefinition) {

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

    /**
     * 设置实例化策略对象
     * @param instantiationStrategy 实例化策略对象
     */
    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

    /**
     * 为Bean对象填充属性
     * 循环属性集合进行填充操作
     * @param beanName  BeanName
     * @param bean      实例化Bean对象
     * @param beanDefinition    BeanDefinition类
     */
    private void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        try {
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                String name = propertyValue.getName();
                Object value = propertyValue.getValue();

                // 如果为对象引用类型 先获取Bean再填充
                if (value instanceof BeanReference) {
                    BeanReference beanReference = (BeanReference) value;
                    value = getBean(beanReference.getBeanName());
                }
                // 对属性进行填充
                BeanUtil.setFieldValue(bean, name, value);
            }
        } catch (Exception e) {
            throw new BeansException("Error setting property values: " + beanName);
        }
    }




    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            Object current = beanPostProcessor.postProcessBeforeInitialization(result, beanName);
            if (current == null) return current;
            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            Object current = beanPostProcessor.postProcessAfterInitialization(result, beanName);
            if (current == null) return current;
            result = current;
        }
        return result;
    }
}
