package com.yancy.springframework.aop;

import org.aopalliance.aop.Advice;

/**
 * @author yancy0109
 */
public interface Advisor {

    Advice getAdvice();
}
