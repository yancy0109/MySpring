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