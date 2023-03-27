package com.yancy.springframework.aop.aspectj;

import com.yancy.springframework.aop.Pointcut;
import com.yancy.springframework.aop.PointcutAdvisor;
import org.aopalliance.aop.Advice;

/**
 *
 * @author yancy0109
 */
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {

    // 切面（通过 expression 实例化并提供比较器）
    private AspectJExpressionPointcut pointcut;

    // 具体拦截方法
    private Advice advice;

    // 表达式
    private String expression;

    @Override
    public Advice getAdvice() {
        return advice;
    }

    @Override
    public Pointcut getPointcut() {
        if (null == pointcut) {
            pointcut = new AspectJExpressionPointcut(expression);
        }
        return pointcut;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }
}
