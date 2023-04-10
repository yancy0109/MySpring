package com.yancy.springframework.core.convert.support;

import com.yancy.springframework.core.convert.converter.ConverterRegistry;

/**
 * 类型转换服务默认类
 * @author yancy0109
 */
public class DefaultConversionService extends GenericConversionService{

    public DefaultConversionService() {
        addDefaultConverters(this);
    }

    public static void addDefaultConverters(ConverterRegistry converterRegistry) {
        // 添加各类类型转换工厂
        converterRegistry.addConverterFactory(new StringToNumberConverterFactory());
    }
}
