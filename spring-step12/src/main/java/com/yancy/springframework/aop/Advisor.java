package com.yancy.springframework.aop;

import org.aopalliance.aop.Advice;

/**
 * 访问者类
 * @author yancy0109
 */
public interface Advisor {

    Advice getAdvice();
}
