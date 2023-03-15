package com.yancy.springframework.test;

import com.yancy.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.yancy.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import com.yancy.springframework.test.bean.UserService;
import org.junit.Test;

/**
 * API测试
 * @author yancy0109
 */
public class ApiTest {

    @Test
    public void test_XmlBeanReader() {
        // 初始化BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 创建配置读取对象 并 读取
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:spring.xml");

        // 获取Bean对象并调用方法
        UserService userService = beanFactory.getBean("userService", UserService.class);
        userService.queryUserInfo();

    }
}
