package com.yancy.springframework.beans.factory;

import com.yancy.springframework.beans.BeansException;
import com.yancy.springframework.beans.PropertyValue;
import com.yancy.springframework.beans.PropertyValues;
import com.yancy.springframework.beans.core.io.DefaultResourceLoader;
import com.yancy.springframework.beans.core.io.Resource;
import com.yancy.springframework.beans.factory.config.BeanDefinition;
import com.yancy.springframework.beans.factory.config.BeanFactoryPostProcessor;

import java.io.IOException;
import java.util.Properties;

/**
 * 处理占位符配置
 */
public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {


    /**
     * Default Prefix
     */
    public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";

    /**
     * Default Suffix
     */
    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

    private String location;

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 加载属性文件
        try {
            DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource(location);
            Properties properties = new Properties();
            properties.load(resource.getInputStream());

            String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
            for (String beanName : beanDefinitionNames) {
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);

                PropertyValues propertyValues = beanDefinition.getPropertyValues();
                for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                    Object value = propertyValue.getValue();
                    if (!(value instanceof String)) continue;
                    String strVal = (String) value;
                    StringBuilder buffer = new StringBuilder(strVal);
                    int startIdx = strVal.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
                    int stopIdx = strVal.indexOf(DEFAULT_PLACEHOLDER_SUFFIX);
                    // 如果为 占位符
                    if (startIdx != -1 && stopIdx != -1 && startIdx < stopIdx) {
                        String propKey = strVal.substring(startIdx + 2, stopIdx);
                        String propVal = properties.getProperty(propKey);
                        buffer.replace(startIdx, stopIdx + 1, propVal);
                        // propertyValue 替换
                        propertyValues.addPropertyValue(
                                new PropertyValue(propertyValue.getName(), buffer.toString())
                        );
                    }
                }
            }
        } catch (IOException e) {
            throw new BeansException("Could not load properties");
        }


    }
}
