package com.yancy.springframework.test;

import com.yancy.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.yancy.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import com.yancy.springframework.context.support.ClassPathXmlApplicationContext;
import com.yancy.springframework.test.bean.UserService;
import org.junit.Test;

/**
 * API测试
 * @author yancy0109
 */
public class ApiTest {

    @Test
    public void test_ApplicationContext() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "classpath:spring.xml"
        );
        applicationContext.registerShutdownHook();

        UserService userService = applicationContext.getBean("userService", UserService.class);
        userService.queryUserInfo();
    }
}
