package com.yancy.springframework.core.convert;

public interface Converter<S,T> {

    /**
     * 将 Source Target 从 S 类型转换为 T类型
     * @param source
     * @return
     */
    T convert(S source);
}
