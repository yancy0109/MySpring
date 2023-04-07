package com.yancy.springframework.test.bean;

import com.yancy.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

public class HusbandMother implements FactoryBean<IMother> {

    @Override
    public IMother getObject() throws Exception {
        return (IMother) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[]{IMother.class},
                (proxy, method, args) ->
                        "婚后媳妇妈妈的指责被婆婆代理类！" + method.getName()
        );
    }

    @Override
    public Class<?> getObjectType() {
        return IMother.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
