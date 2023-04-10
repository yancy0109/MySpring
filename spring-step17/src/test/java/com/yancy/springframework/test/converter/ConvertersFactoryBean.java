package com.yancy.springframework.test.converter;

import com.yancy.springframework.beans.factory.FactoryBean;
import com.yancy.springframework.test.bean.StringToLocalDateConverter;

import java.util.HashSet;
import java.util.Set;

/**
 * @author yancy0109
 */
public class ConvertersFactoryBean implements FactoryBean<Set<?>> {

    @Override
    public Set<?> getObject() throws Exception {
        HashSet<Object> converters = new HashSet<>();
        StringToLocalDateConverter stringToLocalDateConverter = new StringToLocalDateConverter("yyyy-MM-dd");
        converters.add(stringToLocalDateConverter);
        return converters;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
