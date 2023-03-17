package com.yancy.springframework.beans.factory.config;

/**
 * 单例注册接口
 * 定义一个获取单例对象的接口
 * @author yancy0109
 */
public interface SingletonBeanRegistry {

    /**
     * 返回指定名称的（原始）单例对象
     * @param beanName
     * @return
     */
    Object getSingleton(String beanName);

    /**
     * 注册单例对象
     * @param beanName
     * @param singletonObject
     */
    void registerSingleton(String beanName, Object singletonObject);
}
