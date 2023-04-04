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
    public void test_scan() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
          "classpath:spring.xml"
        );
        UserService userService = applicationContext.getBean("userService", UserService.class);
        System.out.println(userService.getToken());
        System.out.println(userService.queryUserInfo());
    }
}
