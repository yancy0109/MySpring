package com.yancy.springframework.beans.factory;

import com.yancy.springframework.beans.BeansException;

public interface BeanFactory {

    Object getBean(String beanName) throws BeansException;

    /**
     *
     * @param beanName      目标BeanName
     * @param requiredType  目标对象类型
     * @return              返回目标类型对象
     * @param <T>
     */
    <T> T getBean(String beanName, Class<T> requiredType);

    /**
     * 对有参构造函数进行实例化
     * @param beanName  对象名称
     * @param args      构造函数参数
     * @return          目标对象
     */
    Object getBean(String beanName, Object ...args) throws BeansException;
}
