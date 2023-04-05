### STEP15

#### 目的

将 AOP 动态代理，融入到 Bean 的生命周期。



### 实现

> 之前我们实现的 AOP 动态代理功能，是在 Bean 对象的生命周期外进行单独处理的，并没有对被代理对象进行设置属性，调用初始化方法等功能，我们只要进行一些简单的转变即可将其融入 Bean 对象的生命周期。

将 Bean 对象按照默认方式全部进行创建以及初始化，将 DefaultAdvistorAutoProxyCreator 实现的方法进行修改。

DefaultAdvistorAutoProxyCreator ：

- 原来：通过 Before 方法根据 Class 进行处理，遍历寻找符合 Advisor 进行处理，创建原对象及代理对象，返回代理对象
- 现在：通过 After 方法，传入原对象，根据对象 Class，遍历寻找符合的 Advisor 进行处理，根据原对象创建代理对象，返回代理对象

