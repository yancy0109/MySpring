package com.yancy.springframework.test;

import com.yancy.springframework.beans.PropertyValue;
import com.yancy.springframework.beans.PropertyValues;
import com.yancy.springframework.beans.factory.config.BeanDefinition;
import com.yancy.springframework.beans.factory.config.BeanReference;
import com.yancy.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.yancy.springframework.test.bean.UserDao;
import com.yancy.springframework.test.bean.UserService;
import org.junit.Test;

/**
 * API测试
 * @author yancy0109
 */
public class ApiTest {

    @Test
    public void test_BeanFactory() {
        // 初始化BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 注册UserDao

        beanFactory.registerBeanDefinition("userDao", new BeanDefinition(UserDao.class));

        // 配置UserService属性
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("uId", "10001"));

        propertyValues.addPropertyValue(new PropertyValue("userDao", new BeanReference("userDao")));
        // 注册UserService

        beanFactory.registerBeanDefinition("userService", new BeanDefinition(UserService.class, propertyValues));

        // 获取userService
        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo();    // 查询用户信息：小明
    }
}
