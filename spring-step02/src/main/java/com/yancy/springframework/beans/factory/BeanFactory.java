package com.yancy.springframework.beans.factory;

/**
 * BeanFactory接口
 * @author yancy0109
 */
public interface BeanFactory {

    Object getBean(String beanName) throws BeansException;

}
