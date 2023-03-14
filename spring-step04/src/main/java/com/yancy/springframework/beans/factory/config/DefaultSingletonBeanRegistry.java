package com.yancy.springframework.beans.factory.config;

import java.util.HashMap;
import java.util.Map;

/**
 * 默认单例注册类
 * 实现了获取单例对象
 * 提供了供子类调用的addSingleton方法
 * @author yancy0109
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry{

    private Map<String, Object> singletonObjects = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    protected void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }
}
