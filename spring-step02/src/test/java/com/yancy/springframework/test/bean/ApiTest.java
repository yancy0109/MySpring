package com.yancy.springframework.test.bean;

import com.yancy.springframework.beans.factory.config.BeanDefinition;
import com.yancy.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.junit.Test;

/**
 * 测试类
 * @author yancy0109
 */
public class ApiTest {

    @Test
    public void test_BeanFactory() {
        // 初始化BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 注册Bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition("userService", beanDefinition);
        // 第一次获取Bean
        UserService userService = (UserService) beanFactory.getBean("userService");
        // 测试
        userService.queryUserInfo();  // 查询用户信息
        // 第二次获取
        UserService userServiceSingleton = (UserService) beanFactory.getBean("userService");
        userServiceSingleton.queryUserInfo();  // 查询用户信息
        // 比较是否相同
        System.out.println(userServiceSingleton == userService);  // true
    }
}
