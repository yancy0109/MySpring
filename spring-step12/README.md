### STEP12
将 AOP 扩展到 Bean 的生命周期。

主要实现方法：通过 BeanProcessor 来实现将原对象替换为代理对象进行调用。



实现：

首先 refresh 会对 BeanProcessor 进行注册，DefaultAdvisorAutoProxyCreator 也会被注册在内，它实现了 BeanProcessor接口功能，也是额外实现了 代理类处理方法。

它会对原对象进行替换，他继承了 BeanFactory感知类，在初始化过程会配置 BeanFacory，提供获取   所有  Advisor 对象的能力。

如果对象为基础类（Advisor ｜ Advised ｜ Pointcut），他并不会提供处理方法。

如果对象为普通对象，他会通过 BeanFactory 获取所有 Advisor 对象，进行遍历获取 Advisor 中 ClassFilter，如果最终无法获取到可处理的 Advisor 对象，会与基础类相同，调用 普通实例化流程。

如果当前实例化对象与 ClassFilter 匹配，它会进行下一步处理

1. 通过构造器反射实例化原对象
2. 创建 AdvisedSupport 对象
3. 提取 Advisor对象 MethodInterceptor
4. 提取 Advisor对象 MethodMatcher
5. 配置是否使用 Cglib
6. 通过AdvisedSupport对象实例化 Proxyfactory 实例化代理对象并返回。

获取所有 Advisor 来进行遍历处理