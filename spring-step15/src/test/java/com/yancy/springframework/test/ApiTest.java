package com.yancy.springframework.test;

import com.yancy.springframework.context.support.ClassPathXmlApplicationContext;
import com.yancy.springframework.test.bean.IUserService;
import org.junit.Test;

/**
 * @author yancy0109
 */
public class ApiTest {

    @Test
    public void test_autoProxy() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "classpath:spring.xml"
        );
        IUserService userService = applicationContext.getBean("userService", IUserService.class);
        System.out.println(userService.queryUserInfo());
    }
}
