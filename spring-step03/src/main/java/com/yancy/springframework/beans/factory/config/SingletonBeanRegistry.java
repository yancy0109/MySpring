package com.yancy.springframework.beans.factory.config;

/**
 * 单例注册接口
 * 定义一个获取单例对象的接口
 * @author yancy0109
 */
public interface SingletonBeanRegistry {

    Object getSingleton(String beanName);
}
