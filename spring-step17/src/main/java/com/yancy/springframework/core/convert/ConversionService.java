package com.yancy.springframework.core.convert;

import com.sun.istack.internal.Nullable;

public interface ConversionService {

    /**
     * 返回 sourceType 是否能被转换为 targetType
     * @param sourceType
     * @param targetType
     * @return
     */
    boolean canConvert(@Nullable Class<?> sourceType, Class<?> targetType);

    /**
     * 将 Source 转换为 指定类型
     * @param source
     * @param targetType
     * @return
     * @param <T>
     */
    <T> T convert(Object source, Class<T> targetType);

}
