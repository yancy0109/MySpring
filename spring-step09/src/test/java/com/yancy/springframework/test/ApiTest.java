package com.yancy.springframework.test;

import com.yancy.springframework.context.support.ClassPathXmlApplicationContext;
import com.yancy.springframework.test.bean.UserService;
import org.junit.Test;

/**
 * API测试
 * @author yancy0109
 */
public class ApiTest {

    @Test
    public void test_prototype() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "classpath:spring.xml"
        );
        applicationContext.registerShutdownHook();

        UserService userService1 = applicationContext.getBean("userService", UserService.class);
        UserService userService2 = applicationContext.getBean("userService", UserService.class);

        System.out.println(userService1);
        System.out.println(userService2);
        System.out.println("userService1: userDao " + userService1.getUserDao());
        System.out.println("userService2: userDao " + userService2.getUserDao());

        System.out.println(userService1 + " 十六进制哈希： " + Integer.toHexString(userService1.hashCode()));
        System.out.println(userService2 + " 十六进制哈希： " + Integer.toHexString(userService2.hashCode()));
    }

    @Test
    public void test_factory_bean() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "classpath:spring.xml"
        );
        applicationContext.registerShutdownHook();

        UserService userService = applicationContext.getBean("userService", UserService.class);
        System.out.println(userService.queryUserInfo());
    }

}
