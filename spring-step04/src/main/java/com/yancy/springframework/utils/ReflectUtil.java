package com.yancy.springframework.utils;

import com.yancy.springframework.beans.BeansException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 参考Hutool实现
 * @author yancy0109
 */
public class ReflectUtil {


    /**
     *
     * @param bean              目标对象
     * @param fieldName  目标属性名称
     * @param value             目标属性值
     * @throws BeansException   BeansException
     */
    public static void setFieldValue(Object bean, String fieldName, Object value) throws BeansException{
        if (bean == null) {
            throw new  BeansException("Bean set Filed Failed");
        }
        if (fieldName == null || fieldName.trim().length() == 0) {
            throw new BeansException("Bean Field Name is Blank");
        }
        final Field field = getField(bean.getClass(), fieldName);
        setFieldValue(bean, field, value);
    }

    /**
     * 通过Filed对象设定Bean的对应属性为Value
     * @param bean              目标对象
     * @param field             Filed对象
     * @param value             Filed对象设定值
     * @throws BeansException   BeansException
     */
    private static void setFieldValue(Object bean, Field field, Object value) throws BeansException{
        if (field == null) {
            throw new BeansException("Set Filed Failed, Filed in " + bean + ", Field not exist");
        }
        // 记录Value及其父类(除Object.class) Class类型
        List<Class<?>> valueClazzList = new ArrayList<>();
        Class<?> clazz = value.getClass();
        while (clazz != Object.class) {
            valueClazzList.add(clazz);
            clazz = clazz.getSuperclass();
        }
        // 判断Filed设定Type是否符合Value对象类型
        if (!valueClazzList.contains(field.getType())) {
            throw new BeansException("Set Filed Failed, Filed in " + bean + " , wrong type for Filed Value");
        }
        try {
            field.setAccessible(true);  // 允许操作私有属性
            field.set(bean instanceof Class ? null : bean, value);
        } catch (IllegalAccessException e) {
            throw new BeansException("Set Field exist Error: " + e);
        }
    }

    /**
     * 遍历目标类及其父类（不包括Object）找到name对应Field
     * @param beanClass         目标类
     * @param name              属性名称
     * @return Filed            返回name对应目标Field
     * @throws BeansException   BeansException
     */
    public static Field getField(Class<?> beanClass, String name) throws BeansException {
        Class<?> clazz = beanClass;
        while (clazz != Object.class) {
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                if (declaredField.getName().equals(name)) return declaredField;
            }
            clazz = beanClass.getSuperclass();
        }
        return null;
    }
}
