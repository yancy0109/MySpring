package com.yancy.springframework.utils;

import com.yancy.springframework.stereotype.Component;

import java.util.Set;

public class ClassUtils {

    public static ClassLoader getDefaultClassLoader() {
        ClassLoader c1 = null;
        try {
            c1 = Thread.currentThread().getContextClassLoader();
        } catch (Throwable ex) {
            // Can not get Thread Context ClassLoader
        }
        if (c1 == null) {
            c1 = ClassUtils.class.getClassLoader();
        }
        return c1;
    }

    /**
     * 检查是否为 Cglib 生成类
     * @param clazz
     * @return
     */
    public static boolean isCglibProxyClass(Class<?> clazz) {
        return (clazz != null && isCglibProxyClassName(clazz.getName()));
    }

    /**
     * 检验 ClassName 是否为 Cglib 生成类名
     * @param className
     * @return
     */
    private static boolean isCglibProxyClassName(String className) {
        return (className != null && className.contains("$$"));
    }

    public static Set<Class<?>> scanPackageByAnnotation(String basePackage, Class<Component> componentClass) {

        return null;
    }
}
