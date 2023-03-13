### STEP02

> 实现了Bean的定义，注册，获取

#### 介绍

对BeanFactory进行进一步完善

将BeanDefinition对象存储Object修改为存储Class，我们就可以在获取Bean的过程中判断Bean对象的实例化与存储情况

存储需要实例化的Singleton对象，如果未能获取到，则根据存储Class信息实例化对象，存储并返回对象。

大致思想如图：

![image-20230313180508602](https://raw.githubusercontent.com/yancy0109/image/main/img/image-20230313180508602.png)

#### 总结

通过模板方法，将固定实现先实现，再交由子类实现分步骤实现了每一个方法对应的实现。

模板方法主要体现在了AbstractBeanFactory

它的子实现

- AbstractAutowireCapableBeanFactory
- DefaultListableBeanFactory

分别实现了两个抽象方法，将每种实现都进行了解耦

![image-20230313213300381](https://raw.githubusercontent.com/yancy0109/image/main/img/image-20230313213300381.png)
