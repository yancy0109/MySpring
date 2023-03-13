package com.yancy.springframework.beans.factory.config;

import lombok.Data;

/**
 * Spring Bean对象
 * @author yancy0109
 */
@Data
public class BeanDefinition {

    private Class beanClass;    // 通过Class对象进行实例化操作

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
    }

}
