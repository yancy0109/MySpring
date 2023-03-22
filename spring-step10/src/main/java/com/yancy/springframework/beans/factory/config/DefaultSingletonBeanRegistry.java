package com.yancy.springframework.beans.factory.config;

import com.yancy.springframework.beans.BeansException;
import com.yancy.springframework.beans.factory.DisposableBean;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 默认单例注册类
 * 实现了获取单例对象
 * 提供了供子类调用的addSingleton方法
 * @author yancy0109
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    /**
     * 单例对象容器
     */
    private final Map<String, Object> singletonObjects = new HashMap<>();

    /**
     * 销毁对象适配器容器
     */
    private final Map<String , DisposableBean> disposableBeans = new HashMap<>();

    /**
     * 单例对象的空对象标记
     * 用作 cocurrentMap 标记值 （不支持 NULL）
     */
    protected static final Object NULL_OBJECT = new Object();

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }

    public void registerDisposableBean(String beanName, DisposableBean bean) {
        disposableBeans.put(beanName, bean);
    }

    @Override
    public void destroySingletons() {
        Set<String> keySet = this.disposableBeans.keySet();
        Object[] disposableBeanNames = keySet.toArray();
        for (int i = disposableBeanNames.length -1; i >= 0; i--) {
            Object beanName = disposableBeanNames[i];
            DisposableBean disposableBean = disposableBeans.remove(beanName);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
            }
        }
    }
}
