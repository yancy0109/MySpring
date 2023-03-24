package com.yancy.springframework.test;
import com.yancy.springframework.aop.AdvisedSupport;
import com.yancy.springframework.aop.MethodMatcher;
import com.yancy.springframework.aop.TargetSource;
import com.yancy.springframework.aop.aspectj.AspectJExpressionPointcut;
import com.yancy.springframework.aop.framework.Cglib2AopProxy;
import com.yancy.springframework.aop.framework.JdkDynamicAopProxy;
import com.yancy.springframework.aop.framework.ReflectiveMethodInvocation;
import com.yancy.springframework.test.bean.IUserService;
import com.yancy.springframework.test.bean.UserService;

import com.yancy.springframework.test.bean.UserServiceInterceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * API测试
 * @author yancy0109
 */
public class ApiTest {

    @Test
    public void test_proxy_method() {
        // 可以为任意对象
        Object targetObject = new UserService();
        // AOP 代理
        IUserService proxy = (IUserService) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                targetObject.getClass().getInterfaces(),
                new InvocationHandler() {

                    // 方法拦截器
                    MethodMatcher methodMatcher = new AspectJExpressionPointcut("execution(* com.yancy.springframework.test.bean.IUserService.*(..))");

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (methodMatcher.matches(method, targetObject.getClass())) {
                            // 方法拦截器
                            MethodInterceptor methodInterceptor = methodInvocation -> {
                                long start = System.currentTimeMillis();
                                try {
                                    return methodInvocation.proceed();
                                } finally {
                                    System.out.println("监控 - Begin By AOP");
                                    System.out.println("方法名称：" + methodInvocation.getMethod().getName());
                                    System.out.println("方法耗时：" + (System.currentTimeMillis() - start) + "ms");
                                    System.out.println("监控 - End\r\n");
                                }
                            };
                            // 反射调用
                            return methodInterceptor.invoke(new ReflectiveMethodInvocation(targetObject, method, args));
                        }
                        return method.invoke(targetObject, args);
                    }
                }
        );
        String result = proxy.queryUserInfo();
        System.out.println(result);
    }


    @Test
    public void test_aop() throws NoSuchMethodException {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution(* com.yancy.springframework.test.bean.UserService.*(..))");
        Class<UserService> clazz = UserService.class;
        Method method = clazz.getDeclaredMethod("queryUserInfo");

        System.out.println(pointcut.matches(clazz));    // true
        System.out.println(pointcut.matches(method, clazz));    // true
    }

    @Test
    public void test_dynamic() {
        // 目标对象

        IUserService userService = new UserService();

        // 组装代理信息
        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(new TargetSource(userService));
        advisedSupport.setMethodInterceptor(new UserServiceInterceptor());
        advisedSupport.setMethodMatcher(
                new AspectJExpressionPointcut("execution(* com.yancy.springframework.test.bean.IUserService.*(..))")
        );

        // 获取代理对象
        IUserService proxy_jdk = (IUserService) new JdkDynamicAopProxy(advisedSupport);
        // 调用
        System.out.println("测试结果: " + proxy_jdk.queryUserInfo());

        // 获取代理对象
        IUserService proxy_cglib = (IUserService) new Cglib2AopProxy(advisedSupport);
        // 调用
        System.out.println("测试结果: " + proxy_cglib.queryUserInfo());


    }
}
