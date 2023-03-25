### STEP11



#### 目的

基于 JDK，Cglib 实现 AOP 代理。



#### 实现

给目标类符合匹配的方法调用方法拦截器，加入自定义逻辑，再反射调用原方法。

##### 切点表达式类

AspectJExpressionPointcut，通过构造函数提供的 String，提供校验方法与构造方法内的目标方法进行匹配。

##### 包装切面通知信息

包装对象：

- 被代理目标对象：原对象
- 方法拦截器：拦截后的处理逻辑
- 方法匹配器：对方法进行匹配

##### 抽象代理实现

> 分别用 JDK 代理，Cglib 代理方式生成对应的代理类

###### JDK 代理

通过实现 Invocation 接口来定义处理逻辑，通过 AdvisedSupport 获得对应的方法匹配器，如果符合则调用 AdvisedSupport对象内部 方法拦截器 进行逻辑处理。否则直接反射调用原对象进行方法调用。



###### Cglib 代理

通过实现 MethodInterceptor 接口定义了 MethodInvocation#intercept 方法，在其中定义了处理逻辑，根据  AdvisedSupport 获得对应的方法匹配器，如果符合则调用 AdvisedSupport对象内部 方法拦截器 进行逻辑处理。否则直接反射调用原对象进行方法调用。



###### 方法拦截器

MethodInterceptor，我们在两种  代理类的处理逻辑对该类进行了调用，我们通过对实现接口来定义了具体处理逻辑，需要注意的是，我们在 MethodInterceptor#invoke 方法中，入参为 MethodInvocation，它是一个具有 proceed 方法的接口，我们对该入参类接口进行了实现，聚合了原对象、原方法与调用参数，即可 通过 MethodInvocation 对原对象进行方法调用。这样在方法拦截器的处理逻辑中，我们就将原方法调用融入了进去。



#### 类图

![Snipaste_2023-03-25_08-40-37](https://raw.githubusercontent.com/yancy0109/image/main/img/Snipaste_2023-03-25_08-40-37.jpg)
