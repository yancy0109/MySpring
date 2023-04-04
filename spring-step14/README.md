### STEP14



### 目的

通过注解注入属性信息



### 实现

#### 对 @Value 特殊处理

上一章我们配置了 PropertyPlaceholderConfigurer，它在 Refresh 过程中进行了提前实例化与调用，用于读取提前配置的 Properties 文件，这样就可以对 BeanDefinition 中占位符属性进行更新，但是如果是注解属性的情况，这个属性不一定提前声明在 BeanDefinition中（Component 注解下注册的 BeanDefinition对象，就没有添加 PropertyValues），所以我们进行了如下处理。

处理：将 Properties 属性聚合至 BeanFactory，由 BeanFactory 提供方法：遍历所有 StringValueResolver 处理 String 对象，如果非占位符则返回原值，如果为占位符则返回匹配 Properties 中对应属性值。该 resolveEmbeddedValueResolver 方法将在对象实例化后，设置属性前处理注解时进行调用。

#### 对注解处理通用流程

实现 InstantiationAwareBeanPostProcessor，BeanFactoryAware 接口，在 Bean 对象实例化完成后，设置属性操作前进行操作

1. 处理 Bean Class 对象，获取真实 Class 对象（ Cglib 通过继承了原始类）
2. 获取 Class 对象所有 Filed，进行遍历，如果 Filed 拥有注解 @Value，则获取 Value 值 交由 ConfigurableBeanFactory#resolveEmbeddedValueResolver 进行处理，返回真实值（如果为通配符则为 Properties 中匹配值，否则为原始值）
3. 通过反射直接设置属性
4. 获取 Class 对象所有 Filed，进行遍历，如果 Filed 拥有注解 @Autowired，则获取该属性类型，再次尝试读取 @Qualifer 注解，根据 BeanName与Type 或者只根据 Type 从BeanFactory中获取依赖对象
5. 通过反射直接设置属性



