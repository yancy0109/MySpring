package com.yancy.springframework.context.annotation;

import cn.hutool.core.util.StrUtil;
import com.yancy.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.yancy.springframework.beans.factory.config.BeanDefinition;
import com.yancy.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.yancy.springframework.stereotype.Component;

import java.util.Set;

/**
 * 继承父类扫描获取 Definition 对象。补全 BeanName，Scope 并注册至 BeanRegisterRegistry
 * @author yancy0109
 */
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {

    private BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void doScan(String... basePackages) {
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            for (BeanDefinition beanDefinition : candidates) {
                // 解析 Bean 的作用域 singleton prototype
                String beanScope = resolveBeanScope(beanDefinition);
                if (StrUtil.isNotEmpty(beanScope)) {
                    beanDefinition.setScope(beanScope);
                }
                registry.registerBeanDefinition(determineName(beanDefinition), beanDefinition);
            }
        }

        // 注册处理注解（@Autowired @Value）的 BeanPostProcessor
        registry.registerBeanDefinition(
                "com.yancy.springframework.context.annotation.internalAutowiredAnnotationProcessor",
                new BeanDefinition(AutowiredAnnotationBeanPostProcessor.class)
        );

    }

    /**
     * 根据 BeanDefinition，返回名称（Component注解，默认 Class#getSimpleName）
     * @param beanDefinition
     * @return Name
     */
    private String determineName(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Component component = beanClass.getAnnotation(Component.class);
        String value = component.value();
        if (StrUtil.isEmpty(value)) {
            value = StrUtil.lowerFirst(beanClass.getSimpleName());
        }
        return value;
    }

    /**
     * 根据 BeanDefiniton，返回 Scope（Scope注解，默认 “” ）
     * @param beanDefinition
     * @return Scope
     */
    private String resolveBeanScope(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Scope scope = beanClass.getAnnotation(Scope.class);
        if (null != scope) return scope.value();
        return StrUtil.EMPTY;
    }
}
