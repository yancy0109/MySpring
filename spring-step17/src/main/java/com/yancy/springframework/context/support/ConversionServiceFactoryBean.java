package com.yancy.springframework.context.support;

import com.sun.istack.internal.Nullable;
import com.yancy.springframework.beans.factory.FactoryBean;
import com.yancy.springframework.beans.factory.InitializingBean;
import com.yancy.springframework.core.convert.ConversionService;
import com.yancy.springframework.core.convert.converter.Converter;
import com.yancy.springframework.core.convert.converter.ConverterFactory;
import com.yancy.springframework.core.convert.converter.ConverterRegistry;
import com.yancy.springframework.core.convert.converter.GenericConverter;
import com.yancy.springframework.core.convert.support.DefaultConversionService;
import com.yancy.springframework.core.convert.support.GenericConversionService;

import java.util.Set;

/**
 * @author yancy0109
 */
public class ConversionServiceFactoryBean implements FactoryBean<ConversionService>, InitializingBean {

    @Nullable
    private Set<?> converters;

    @Nullable
    private GenericConversionService conversionService;

    @Override
    public ConversionService getObject() throws Exception {
        return conversionService;
    }

    @Override
    public Class<?> getObjectType() {
        return conversionService.getClass();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.conversionService = new DefaultConversionService();
        registerConverters(converters, conversionService);
    }

    private void registerConverters(Set<?> converters, ConverterRegistry registry) {
        if (converters != null) {
            for (Object converter : converters) {
                if (converter instanceof GenericConverter) {
                    registry.addConverter((GenericConverter) converter);
                } else if (converter instanceof Converter<?, ?>) {
                    registry.addConverter((Converter<?, ?>) converter);
                } else if (converter instanceof ConverterFactory<?, ?>) {
                    registry.addConverterFactory((ConverterFactory<?, ?>) converter);
                } else {
                    throw new IllegalStateException(
                            "Each converter object must implement one of the " +
                            "Converter, ConverterFactory, or GenericConverter interfaces"
                    );
                }
            }
        }
    }

    public void setConverters(Set<?> converters) {
        this.converters = converters;
    }
}
