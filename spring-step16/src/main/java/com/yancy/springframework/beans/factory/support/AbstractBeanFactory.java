package com.yancy.springframework.beans.factory.support;

import com.yancy.springframework.beans.BeansException;
import com.yancy.springframework.beans.factory.FactoryBean;
import com.yancy.springframework.beans.factory.config.BeanDefinition;
import com.yancy.springframework.beans.factory.config.BeanPostProcessor;
import com.yancy.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.yancy.springframework.utils.ClassUtils;
import com.yancy.springframework.utils.StringValueResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象Bean工厂
 * 模板方法
 * @author yancy0109
 */
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    /**
     * String Resolvers，用于处理 例如 注解属性
     */
    private final List<StringValueResolver> embeddedValueResolvers = new ArrayList<>();

    /**
     * BeanPostProcessor 容器
     */
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    /**
     * ClassLoader 用于解析 bean class names
     */
    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    @Override
    public Object getBean(String beanName) throws BeansException {
        return doGetBean(beanName, null);
    }

    @Override
    public <T> T getBean(String beanName, Class<T> requiredType) {
        return (T) getBean(beanName);
    }

    @Override
    public Object getBean(String beanName, Object... args) throws BeansException {
        return doGetBean(beanName, args);
    }

    protected <T> T doGetBean(final String beanName, Object[] args) {
        Object sharedInstance = super.getSingleton(beanName);
        if (sharedInstance != null) {
            // 如果为 FactoryBean  则需要调用 FactoryBean#getObject
            return (T) getObjectFromBeanInstance(sharedInstance, beanName);
        }
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        Object bean = this.createBean(beanName, beanDefinition, args);
        // 再次判断是否为 FactoryBean
        return (T) getObjectFromBeanInstance(bean, beanName);
    }

    private Object getObjectFromBeanInstance(Object beanInstance, String beanName) {
        if (!(beanInstance instanceof FactoryBean)) {
            return beanInstance;
        }
        Object object = getCacheObjectFromFactoryBean(beanName);

        if (object == null) {
            FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
            object = getObjectFromFactoryBean(factoryBean, beanName);
        }

        return object;
    }

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException;

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }

    @Override
    public void addEmbeddedValueResolver(StringValueResolver valueResolver) {
        this.embeddedValueResolvers.add(valueResolver);
    }

    @Override
    public String resolveEmbeddedValueResolver(String value) {
        String result = value;
        for (StringValueResolver resolver : this.embeddedValueResolvers) {
            // 如果 Value 为占位符 String 类型，则遍历找到匹配 Properties 进行填充
            result = resolver.resolveStringValue(result);
        }
        return result;
    }
}
