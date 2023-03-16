package com.yancy.springframework.context;

import com.yancy.springframework.beans.BeansException;

public interface ConfigurableApplicationContext extends ApplicationContext{

    /**
     * 容器刷新
     * @throws BeansException
     */
    void refresh() throws BeansException;
}
