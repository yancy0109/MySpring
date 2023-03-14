### STEP04

对AbstractAutowireCapableBeanFactory抽象类进行了补充
扩充了CreateBean方法 
增加applyPropertyValues方法
对通过策略对象创建的目标对象通过applyPropertyValues方法进行属性注入  
通过beanDefinition内PropertyValues集合获取对应的Object
可能情况:
- Object：通过反射，根据目标属性名称获取Field对象进行注入
- BeanReference：先通过BeanReference获取BeanName，在容器中获取或新建Bean对象后，再通过反射，根据目标属性名称获取Field对象进行注入

使用到工具类：
- BeanUtil：对Map/List/Object三种情况进行注入
- ReflectUtil：具体实现注入Object属性

Eg. ReflectUtil获取目标Filed对象

```java
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
```