### STEP16

#### 目的

解决循环依赖



#### 实现

通过实现三级缓存：

- SingletonObjects：完整对象
- EarlySingletonObject：半成品对象
- ObjectFactory：对象工厂，用于获取对象

循环依赖流程 ：A依赖B，B依赖A

1. 实例化对象 A
2. 在 ObjectFactory 注册该对象工厂，返回方法：遍历 Processor 处理，如果有 Advisor 进行处理则返回代理类对象，否则返回原对象
3. 填充属性
4. 再次获取对象 B
   1. 实例化对象 B
   2. 在 ObjectFactory 注册该对象工厂
   3. 填充属性
   4. 获取对象 A
   5. 从 ObjectFactory 中获取到 A 工厂，调用其方法返回 真实对象 / 工厂对象，并将该对象置入 二级缓存
   6. getSingleton 获取到 B 工厂，将其放入一级缓存
   7. 返回 B 对象
5. 通过 getSingleton 获取对象，从 二级缓存中获取到了对象，并将其放入了一级缓存并返回



我觉得很妙的地方：

三级缓存的设置：配置了一个暂存引用的工厂，这个工厂会遍历所有 Processor，如果有 Advisor 进行匹配，则会返回根据引用生成的代理对象，否则返回了原对象。如果实例化过程配置属性开始了新建另一个对象，并且涉及对之前对象的循环引用，则会直接工厂对象获得最终对象（原对象 / 代理对象），并会在 ProxyCreator 进行标记，并将最终对象引用放入了 二级缓存，在原对象处理到最终步骤的时候，也会尝试获取代理类对象，如果之前已经在 ProxyCreator 进行了标记，则会跳过这一过程，最终直接从二级缓存获取对象。否则也会进行主动获取对象。

备注：

一开始有一个问题，如果没有被其他对象引用，那么对象岂不是通过 Cglib 创建两次代理类对象（ProcessorsAfterInitialization，getSingleton），后来通过 Debug 却发现两个对象确实相同的，于是去问了下 ChatGPT，得到了如下回答：

> Cglib 会对重复创建 AOP 代理对象进行优化吗？
>
> 是的，Cglib 会对重复创建 AOP 代理对象进行优化。在使用 Cglib 创建代理对象时，Cglib 会缓存已经生成过的代理类和对应的 ClassLoader，并通过代理类名称和 ClassLoader 来判断是否需要重新生成代理类。如果已经存在相同的代理类，则直接返回该代理类的实例；否则就会重新生成一个代理类。这种方式称为“单例代理”。
>
> 因此，在使用 Cglib 创建代理对象时，即使有多个切面需要拦截和增强同一个 Bean 的方法，也只会生成一个代理类，从而避免了重复创建代理对象的问题。这种优化机制可以提高系统性能并减少内存占用。不过，需要注意的是，如果每个切面都配置了不同的 Advisor，那么仍然会生成多个不同的代理类。



注意：

AOP 代理的实现方式替换为了 Cglib动态代理，原因：

在之前的问题中，我们并未涉及到循环依赖的问题，但是事实上我们可能会将对象依赖于另一个对象，这个对象可能还未创建完成，如果是在创建过程中涉及到了设置属性且属性为 配置了 AOP 代理的对象，那么我们会将代理类型强转进行属性设置，如果为 JDK代理方法实现，因为 AOP 实现方式并未依靠于一个固定接口，所以我们生成的 JDK代理对象不能依靠于强转进行属性设置，会抛出 ClassCastException 异常，如果是 Cglib 生成代理，因为是通过继承的方式进行了实现，所以可以通过强转进行属性配置与调用。

对象实例化策略切换为了 SimpleInstantiationStrategy，原因：

因为上文选择了 Cglib 进行代理类生成，所以我们通过反射进行实例化，就避免了 生成代理类时对目标对象真实接口的复杂判断。