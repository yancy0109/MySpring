package com.yancy.springframework.test;
import com.yancy.springframework.aop.aspectj.AspectJExpressionPointcut;
import com.yancy.springframework.test.bean.IUserService;
import com.yancy.springframework.test.bean.UserService;
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
        Object targetObject = new UserService();

        IUserService proxy = (IUserService) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                targetObject.getClass().getInterfaces(),
                new InvocationHandler() {

                    // 方法拦截器
//                    MethodMacher
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return null;
                    }
                }
        );
    }


    @Test
    public void test_aop() throws NoSuchMethodException {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution(* com.yancy.springframework.test.bean.UserService.*(..))");
        Class<UserService> clazz = UserService.class;
        Method method = clazz.getDeclaredMethod("queryUserInfo");

        System.out.println(pointcut.matches(clazz));    // true
        System.out.println(pointcut.matches(method, clazz));    // true
    }
}
