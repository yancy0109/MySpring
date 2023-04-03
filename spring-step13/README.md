### STEP13

#### 目的  
- 自动扫描 Bean 对象注册
- 对象属性通过设置占位符，通过配置文件配置

#### 实现  

##### 自动注册

在 XmlBeanDefinitionReader#doLoadBeanDefinitions 过程中，先读取 component-casn 下定义的路径，通过 ClassPathBeanDefinitionScanner 进行扫描注册

功能流程：

1. 解析 scanPath，得到 basePackage 数组，进行遍历读取
2. 读取指定路径下带有 Component.class 注解 所有Class 对象，返回包装 BeanDefinition 对象
3. 遍历 BeanDefinition 对象
4. 根据 Class 对象，读取 Scope 注解 ，返回 value（作用域），为空则不设定
5. 根据 Class 对象，读取 Component 注解，返回 value（BeanName），为空则不设定
6. 通过 BeanDefinitionRegister 进行注册

##### 属性占位符

实现 BeanFactortyProcessor 接口，在实例化前进行调用流程。

通过 配置文件 读取 BeanDefinition 进行提前注册，并进行了调用。

功能流程：

1. 读取 Location 位置 Properties 文件
2. 遍历 BeanFactory 所有 BeanDefinition
3. 遍历每一个 BeanDefinition 所有 Property
4. 一旦 Property Value 为 String 类型，且命名规则符合 ${}，则通过 Properties 读取到对应 Key 进行 Value 填充
