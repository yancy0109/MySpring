package com.yancy.springframework.beans.factory.config;

import com.yancy.springframework.beans.PropertyValues;
import lombok.Data;

/**
 * Spring Bean对象
 * @author yancy0109
 */
@Data
public class BeanDefinition {

    private Class beanClass;    // 通过Class对象进行实例化操作

    private PropertyValues propertyValues;  // 对实例化对象的属性对象进行存储

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
        this.propertyValues = new PropertyValues();
    }

    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }
}
