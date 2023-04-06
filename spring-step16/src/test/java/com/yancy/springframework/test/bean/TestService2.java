package com.yancy.springframework.test.bean;

/**
 * @author yancy0109
 */
public class TestService2 {

    private TestService1 testService1;

    @Override
    public String toString() {
        return "TestService2 "+ this.hashCode() + " {" +
                "testService1=" + testService1.hashCode() +
                '}';
    }
}
