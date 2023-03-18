package com.yancy.springframework.test.processor;

import com.yancy.springframework.beans.BeansException;
import com.yancy.springframework.beans.factory.config.BeanPostProcessor;
import com.yancy.springframework.test.bean.UserService;

public class MyBeanProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        if ("userService".equals(beanName)) {
            UserService userService = (UserService) bean;
            userService.setLocation("（改）北京");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
