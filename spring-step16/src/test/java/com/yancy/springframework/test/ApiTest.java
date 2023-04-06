package com.yancy.springframework.test;

import com.yancy.springframework.context.support.ClassPathXmlApplicationContext;
import com.yancy.springframework.test.bean.TestService2;
import org.junit.Test;

/**
 * @author yancy0109
 */
public class ApiTest {

    @Test
    public void test_reRely() {
        // 修改前, 循环依赖会导致发生 StackOverflowError
        // 因为会导致不停递归调用 getBean, 不停创建新对象, 但是新对象也需要 getBean 获取 依赖于它自己的新对象
        try {
            ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
              "classpath:spring.xml"
            );
                applicationContext.getBean("userService", TestService2.class);
        } catch (StackOverflowError e) {
            e.printStackTrace();
        }
    }


}
