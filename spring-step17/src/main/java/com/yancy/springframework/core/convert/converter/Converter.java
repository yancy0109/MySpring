package com.yancy.springframework.core.convert.converter;

/**
 * 类型转换处理接口
 * @param <S>
 * @param <T>
 */
public interface Converter<S,T> {

    /**
     * 将 Source Target 从 S 类型转换为 T类型
     * @param source
     * @return
     */
    T convert(S source);
}
