package com.yancy.springframework.utils;

import com.yancy.springframework.beans.BeansException;

import java.util.List;
import java.util.Map;

/**
 * 参考Hutool实现
 * @author yancy0109
 */
public class BeanUtil {

    /**
     * 将传入Bean对象的FieldNameOrIndex对应属性设定为Value
     * @param bean              Bean
     * @param fieldName  字段名或序号，序号支持负数
     * @param value             值
     */
    public static void setFieldValue(Object bean, String fieldName, Object value) throws BeansException {
        if (bean instanceof Map) {
            ((Map) bean).put(fieldName, value);
            return;
        }
        if (bean instanceof List) {
            ((List) bean).add(value);
        }
        // 普通Bean对象，通过反射添加元素
        ReflectUtil.setFieldValue(bean, fieldName, value);
    }
}
