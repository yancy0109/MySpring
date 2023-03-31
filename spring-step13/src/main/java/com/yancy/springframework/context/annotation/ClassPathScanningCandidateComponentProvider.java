package com.yancy.springframework.context.annotation;

import com.yancy.springframework.beans.factory.config.BeanDefinition;
import com.yancy.springframework.stereotype.Component;
import com.yancy.springframework.utils.ClassUtils;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 处理包装对象扫描
 */
public class ClassPathScanningCandidateComponentProvider {

    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        Set<Class<?>> classes  = ClassUtils.scanPackageByAnnotation(basePackage, Component.class);
        for (Class<?> clazz : classes) {
            candidates.add(new BeanDefinition(clazz));
        }
        return candidates;
    }
}
