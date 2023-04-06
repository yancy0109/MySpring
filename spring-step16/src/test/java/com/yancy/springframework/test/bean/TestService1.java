package com.yancy.springframework.test.bean;

/**
 * @author yancy0109
 */
public class TestService1 {

    private TestService2 testService2;

    @Override
    public String toString() {
        return "TestService1 "+ this.hashCode() + " {" +
                "testService2=" + testService2.hashCode() +
                '}';
    }
}
