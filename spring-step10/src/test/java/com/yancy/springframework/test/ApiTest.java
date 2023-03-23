package com.yancy.springframework.test;

import com.yancy.springframework.context.support.ClassPathXmlApplicationContext;
import com.yancy.springframework.test.event.CustomEvent;
import org.junit.Test;

/**
 * API测试
 * @author yancy0109
 */
public class ApiTest {

    @Test
    public void test_event() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "classpath:spring.xml"
        );
        applicationContext.registerShutdownHook();

        applicationContext.publishEvent(new CustomEvent(applicationContext, 114514L, "成功了！"));
    }

}
