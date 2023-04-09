package com.yancy.springframework.test;

import com.yancy.springframework.context.support.ClassPathXmlApplicationContext;
import com.yancy.springframework.test.bean.Husband;
import com.yancy.springframework.test.bean.Wife;
import org.junit.Test;

/**
 * @author yancy0109
 */
public class ApiTest {

    @Test
    public void test_circular() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "classpath:springThreeLevel.xml"
        );
        Husband husband = applicationContext.getBean("husband", Husband.class);
        Wife wife = applicationContext.getBean("wife", Wife.class);
        System.out.println("老公的媳妇: " + husband.queryWife());
        System.out.println("媳妇的老公: " + wife.queryHusband());
    }


}
