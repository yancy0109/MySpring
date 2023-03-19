### STEP07
实现了初始化与销毁方法

#### 初始化/销毁方法
实现的两者方法
- 接口方法 InitializingBean / DisposableBean
- 配置文件定义 init-method / destroy-method
#### 流程
- 首先对XmlBeanDefinitionReader读取Xml进一步修改，增加BeanDefinition中的两个初始化or销毁方法字段，在读取Xml过程中进行保存Definition至DefaultListableBeanFactory中

- 对AbstractAutowireCapableBeanFactory创建Bean实现方法中进行修改，调用Bean实现的InitializingBean接口/反射调用BeanDefinition指定方法，再创建对象后将BeanDefinition通过**适配器类DisposableBeanAdapter**进行包装，将反射调用与接口调用统一成包装类destroy方法，将对象保存至父类DefaultSingletonBeanRegistry中属性保存，并且由父类提供了统一调用的方法进行销毁方法调用
- ConfigurableApplicationContext新增接口：close，registerShutdownHook方法
  - close：对Register接口提供的统一销毁方法进行调用的封装
  - registerShutdownHook：将close方法进行虚拟机关闭钩子注册

这样我们就实现了初始化与销毁方法。

![image-20230319161148831](https://raw.githubusercontent.com/yancy0109/image/main/img/image-20230319161148831.png)