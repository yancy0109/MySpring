package com.yancy.springframework.context;

import com.yancy.springframework.beans.BeansException;

/**
 * Application主接口
 * 定义了refresh方法
 */
public interface ConfigurableApplicationContext extends ApplicationContext{

    /**
     * 容器刷新
     * @throws BeansException
     */
    void refresh() throws BeansException;
}
