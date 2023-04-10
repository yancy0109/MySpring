package com.yancy.springframework.core.convert.converter;

public interface ConverterFactory<S, R> {

    /**
     * 获得 Converter（T --> S），T 也为 R子类
     * @param targetType
     * @return
     * @param <T>
     */
    <T extends R> Converter<S, T> getConvert(Class<T> targetType);

}
