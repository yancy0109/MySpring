package com.yancy.springframework.context.annotation;

import cn.hutool.core.util.ClassUtil;
import com.yancy.springframework.beans.factory.config.BeanDefinition;
import com.yancy.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 处理包装对象扫描
 * 根据 basePackage，扫描 @Component 注解对象
 */
public class ClassPathScanningCandidateComponentProvider {

    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        // 扫描指定包路径下所有包含指定注解的类
        Set<Class<?>> classes  = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        for (Class<?> clazz : classes) {
            // 创建包含指定 Class BeanDefinition
            candidates.add(new BeanDefinition(clazz));
        }
        return candidates;
    }
}
