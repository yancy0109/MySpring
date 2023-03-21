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

    private String initMethodName;  // 实例对象初始方法

    private String destroyMethodName;   // 实例对象销毁方法

    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public String getDestroyMethodName() {
        return destroyMethodName;
    }

    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }

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
