### STEP08

#### 目的

在Spring框架中提供一种能感知容器操作的接口，只要实现这个接口，就可以获得接口入参中的各种能力。



#### 接口

Aware

- BeanFactoryAware
- BeanClassLoaderAware
- BeanNameWare
- ApplicationContextAware

#### 实现

- ApplicationContextAware：ApplicationContext需要在refresh时才能获取到，因为在refresh操作中提前注册一个BeanPostProcessor，在创建Bean过程中会再通过BeannPostProcessor对Bean进行处理
- 其他接口：都可以在createBean过程中获取到对应的对象，通过 intanceof 判断后进行强转来调用实现的接口方法。

#### 类图

![image-20230320201724762](https://raw.githubusercontent.com/yancy0109/image/main/img/image-20230320201724762.png)
