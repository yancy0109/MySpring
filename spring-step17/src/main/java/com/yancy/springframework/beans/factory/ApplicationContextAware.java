package com.yancy.springframework.beans.factory;

import com.yancy.springframework.beans.BeansException;
import com.yancy.springframework.context.ApplicationContext;

/**
 * 容器感知类：感知到所属的ApplicationContext
 * @author yancy0109
 */
public interface ApplicationContextAware extends Aware {

    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
