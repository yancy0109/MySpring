package com.yancy.springframework.test;

import com.yancy.springframework.context.support.ClassPathXmlApplicationContext;
import com.yancy.springframework.test.bean.Husband;
import org.junit.Test;

/**
 * @author yancy0109
 */
public class ApiTest {

    @Test
    public void test_convert() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "classpath:spring.xml"
        );
        Husband husband = applicationContext.getBean("husband", Husband.class);
        System.out.println(husband);
    }
}
