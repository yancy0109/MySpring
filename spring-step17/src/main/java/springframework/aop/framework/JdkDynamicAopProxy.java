package springframework.aop.framework;

import com.yancy.springframework.aop.AdvisedSupport;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK 实现代理
 * @author yancy0109
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    /**
     * 切面通知信息
     */
    private final AdvisedSupport advised;

    public JdkDynamicAopProxy(AdvisedSupport advised) {
        this.advised = advised;
    }

    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(
                // ClassLoader
                Thread.currentThread().getContextClassLoader(),
                // 接口信息
                advised.getTargetSource().getTargetClass(),
                // Invocation#invoke 实现类
                this
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 如果 方法匹配 成功
        if (advised.getMethodMatcher().matches(method, advised.getTargetSource().getTarget().getClass())) {
            // 方法拦截器
            MethodInterceptor methodInterceptor = advised.getMethodInterceptor();
            // 通过方法拦截器进行处理 并内含 ReflectiveMethodInvocation#process 调用原对象方法
            return methodInterceptor.invoke(new ReflectiveMethodInvocation(advised.getTargetSource().getTarget(), method, args));
        }
        // 否则调用原方法
        return method.invoke(advised.getTargetSource().getTarget(), args);
    }
}
