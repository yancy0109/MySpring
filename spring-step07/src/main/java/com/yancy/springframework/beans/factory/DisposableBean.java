package com.yancy.springframework.beans.factory;

import com.yancy.springframework.beans.BeansException;

/**
 * 销毁方法接口
 * @author yancy0109
 */
public interface DisposableBean {


    /**
     * Bean 销毁方法
     * @throws BeansException
     */
    void destroy() throws BeansException;
}
