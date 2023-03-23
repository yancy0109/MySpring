package com.yancy.springframework.test.bean;

import com.yancy.springframework.beans.factory.FactoryBean;

import javax.jws.Oneway;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * UserDao 工厂
 * @author yancy0109
 */
public class ProxyBeanFactory implements FactoryBean<IUserDao> {

    @Override
    public IUserDao getObject() throws Exception {

        InvocationHandler handler = (Object proxy, Method method, Object[] args) -> {
            // 添加排除方法
            if ("toString".equals(method.getName())) return this.toString();
            Map<String, String> hashMap = new HashMap<>();
            hashMap.put("10001", "小李");
            hashMap.put("10002", "小白");
            hashMap.put("10003", "小新");
            hashMap.put("10004", "小乔");

            return "你被代理了 " + method.getName() + "：" + hashMap.get(args[0].toString());
        };

        return (IUserDao) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                                                 new Class[]{IUserDao.class},
                                                 handler);
    }

    @Override
    public Class<?> getObjectType() {
        return IUserDao.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
