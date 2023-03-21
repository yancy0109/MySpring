### STEP 09

#### 目的
- 实现对象作用域
- 提供一个Bean对象接口，让我们管理对象不再是我们定义好的对象，而是注入FactoryBean，由它来自主创建返回对象，便于其他服务在此标准下接入。

#### 实现方式

##### 实现对象作用域

为BeanDefinition添加scope属性，在读取完BeanDefinition创建对象时判断scope，如果非单例模式，则不放入父类DefaultSingletonBeanRegistry进行保存

##### FactoryBean对象

首先是FactoryBean接口定义三个方法：

- T getObject() throws Exception;	通过 FactoryBean 返回需要的Bean
- Class<?> getObjectType();              FactoryBean 提供的Bean类型
- boolean isSingleton();                     FactoryBean 提供的Bean是否为单例模式

它相当于是一个更复杂的Bean对象，而对于这种类型的Bean对象，我们不能直接返回，而是应该再调用内部方法来返回需要的对象，但是对于它本身，我们仍可以通过DefaultSingletonBeanRegistry进行保存等处理。

所以我们在DefaultSingletonBeanRegistry与AbstractBeanFactory之间添加了一层，专门处理FactoryBean对象，提供方法：

- 在内存统一保存Factory保存的单例Bean对象
- 提供方法供FactoryBean创建需要的Bean对象

这样我们创建BeanDefinition对应Bean仍然是通过AbstractBeanFactory子类来实现，但对创建完成后，如果Bean属于 FactoryBean，就要调用父类方法来进行获取真正需要的对象。

#### 类图

![image-20230321203235387](https://raw.githubusercontent.com/yancy0109/image/main/img/image-20230321203235387.png)