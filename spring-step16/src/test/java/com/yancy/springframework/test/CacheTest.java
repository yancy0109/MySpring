package com.yancy.springframework.test;

import com.yancy.springframework.test.bean.TestService1;
import com.yancy.springframework.test.bean.TestService2;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 一级缓存 Spring
 * @author yancy0109
 */
public class CacheTest {

    private final static Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

    private static <T> T getBean(Class<T> beanClass) throws Exception {
        String beanName = beanClass.getSimpleName().toLowerCase();
        if (singletonObjects.containsKey(beanName)) {
            return (T) singletonObjects.get(beanName);
        }
        // 实例化对象放入缓存
        Object obj = beanClass.newInstance();
        singletonObjects.put(beanName, obj);
        // 属性填充
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Class<?> fieldClass = field.getType();
            String filedBeanName = fieldClass.getSimpleName().toLowerCase();
            field.set(obj, singletonObjects.containsKey(filedBeanName) ? singletonObjects.get(filedBeanName) : getBean(fieldClass));
            field.setAccessible(false);
        }
        return (T) obj;
    }

    @Test
    public void testCache() throws Exception{
        System.out.println(getBean(TestService1.class));
        System.out.println(getBean(TestService2.class));
    }

}
