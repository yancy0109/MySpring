package com.yancy.springframework.test;

import com.yancy.springframework.BeanDefinition;
import com.yancy.springframework.BeanFactory;
import org.junit.Test;

public class    BeanContainerTest {

    @Test
    public void test_BeanFactory() {
        // 创建BeanFactory
        BeanFactory beanFactory = new BeanFactory();
        // 注册Bean
        BeanDefinition beanDefinition = new BeanDefinition(new UserService());
        beanFactory.registerBeanDefinition("userService", beanDefinition);
        // 获取Bean
        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo();
    }
}
