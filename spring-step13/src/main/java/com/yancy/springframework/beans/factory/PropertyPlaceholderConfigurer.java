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
     * Default Prefix : {@value}
     */
    public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";

    /**
     * Default Suffix : {@value}
     */
    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

    /**
     *  location of reading
     */
    private String location;

    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * 读取 Location 位置下 x.Properties，遍历存储的每个 BeanDefinition，对 String 类型 PropertyValue 进行比较
     * 如果 PopertyValue 属性为占位符，则读取 Properties 中符合 propKey 的属性，通过 PropertyValue 进行替换
     * @param beanFactory
     * @throws BeansException
     */
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
