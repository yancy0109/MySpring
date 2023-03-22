STEP10

容器事件和事件监听器



#### 目的

实现Event事件功能，提供事件的定义、发布与监听来完成一些自定义动作。将两件连续性发生的事件进行解耦，交由Spring事件来监听触发对应的事件。我们将通过观察者模式来实现 Spring Event 容器事件与事件监听功能。



#### 实现

###### 事件对象

以 ApplicationEvent 为所有事件类的父类，提供了几种实现：

- ApplicationContextEvent：所有事件的默认实现，由具体实现继承，包括ContextClosedEvent与ContexRefreshEvent
- ContextClosedEvent：监听关闭
- ContextRefreshEvent：监听刷新

###### 监听者对象

是一个泛型接口，泛型类指明了具体事件类型，提供了事件发生的处理方法。

###### 事件广播器

实现了观察者模式，将所有观察者对象保存在一个集合内，通过统一的接口方法，对匹配事件的观察者发送消息（调用）。

存储了 ApplicationListener 对象，提供了广播方法，可以根据发生的 Event 找到匹配的 Listener 对象，并调用 Listener#onApplication(E event)方法。

关于广播方法的实现，通过 Event 遍历聚合的所有 Listener 对象，找到实现的泛型接口的泛型真实类值为 Event 的父类或自身类。

###### 发布者接口

定义了发布事件接口，我们由 ApplicationContext 接口对其继承，由子类实现具体方法，其实最终实现就是调用了**事件广播器**的广播方法。

###### 流程

省略了不相关流程

1. 在 XML 定义了 Listener，读取为 BeanDefinition
2. 初始化 **事件广播器**
3. 提前初始化所有 ApplicationListener 对象，将其注册至 **事件广播器**
4. 完成初始化后，调用刷新完成事件，通过 **事件广播器** 找到匹配的 监听器 调用实现方法
5. 运行阶段，如果发布时间，则通过 **事件广播器** 找到匹配 监听器 调用实现方法
6. 容器关闭前发布 关闭事件，通过 **事件广播器** 找到匹配 监听器 调用实现方法。

##### 类图

![image-20230322200333654](https://raw.githubusercontent.com/yancy0109/image/main/img/image-20230322200333654.png)