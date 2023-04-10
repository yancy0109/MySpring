package com.yancy.springframework.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import com.yancy.springframework.beans.BeansException;
import com.yancy.springframework.beans.PropertyValue;
import com.yancy.springframework.core.io.Resource;
import com.yancy.springframework.core.io.ResourceLoader;
import com.yancy.springframework.beans.factory.config.BeanDefinition;
import com.yancy.springframework.beans.factory.config.BeanReference;
import com.yancy.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import com.yancy.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.yancy.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(Resource resource) throws BeansException {
        try {
            try (InputStream is = resource.getInputStream()) {
                doLoadBeanDefinitions(is);
            }
        } catch (IOException | ClassNotFoundException | DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void loadBeanDefinitions(Resource... resources) throws BeansException {
        for (Resource resource : resources) {
            loadBeanDefinitions(resource);
        }
    }

    @Override
    public void loadBeanDefinitions(String location) throws BeansException {
        ResourceLoader resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinitions(resource);
    }

    protected void doLoadBeanDefinitions(InputStream inputStream) throws ClassNotFoundException, DocumentException {
        // Dom4j 便于加载和解析XML
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        Element root = document.getRootElement();

        // 解析 context:component-scan 标签，扫描包中的类并提取相关信息，最终组装成 BeanDefinition
        Element componentScan = root.element("component-scan");
        if (componentScan != null) {
            String scanPath = componentScan.attributeValue("base-package");
            if (StrUtil.isEmpty(scanPath)) {
                throw new BeansException("The value of base-package attribute can not be empty or null");
            }
            scanPackage(scanPath);
        }

        List<Element> beanList = root.elements("bean");
        for (Element bean : beanList) {
            // Bean标签解析
            String id = bean.attributeValue("id");
            String name = bean.attributeValue("name");
            String className = bean.attributeValue("class");
            // 增加读取init-method destroy-method读取
            String initMethod = bean.attributeValue("init-method");
            String destroyMethod = bean.attributeValue("destroy-method");
            String beanScope = bean.attributeValue("scope");

            // 获取Class对象, 便于获取类中的名称
            Class<?> clazz = Class.forName(className);
            // 容器BeanName优先级 id > name
            String beanName = StrUtil.isNotBlank(id) ? id : name;
            if (StrUtil.isEmpty(beanName)) {
                // 默认名字 Class#getSimpleName
                beanName = StrUtil.lowerFirst(clazz.getSimpleName());
            }

            // 定义 BeanDefinition
            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            // 设定init-method destroy-method scope
            beanDefinition.setInitMethodName(initMethod);
            beanDefinition.setDestroyMethodName(destroyMethod);
            if (StrUtil.isNotEmpty(beanScope)) {
                beanDefinition.setScope(beanScope);
            }
            // 读取属性并填充
            List<Element> propertyList = bean.elements("property");
            for (Element property : propertyList) {
                // 解析Property标签
                String attrName = property.attributeValue("name");
                String attrValue = property.attributeValue("value");
                String attrRef = property.attributeValue("ref");
                // 获取属性值： 引用类型 / 值对象
                Object value = StrUtil.isNotBlank(attrRef) ? new BeanReference(attrRef) : attrValue;
                // 创建属性信息
                PropertyValue propertyValue = new PropertyValue(attrName, value);
                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
            }
            // 检验BeanName是否重复
            if (getRegistry().containsBeanDefinition(beanName)) {
                throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed.");
            }
            // 注册BeanDefinition
            getRegistry().registerBeanDefinition(beanName, beanDefinition);
        }
    }

    private void scanPackage(String scanPath) {
        String[] basePackage = StrUtil.splitToArray(scanPath, ',');
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(getRegistry());
        scanner.doScan(basePackage);
    }

    public void loadBeanDefinitions(String[] locations) {
        for (String location : locations) {
            loadBeanDefinitions(location);
        }
    }
}
