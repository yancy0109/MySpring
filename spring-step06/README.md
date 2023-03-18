### STEP06
实现上下文
预处理
- BeanFactoryPostProcessor：BeanDefinition注册后调用
- BeanPostProcessor：Bean实例化过程中调用

AbstractApplication：实现Refresh功能，使用模版方法模式，定义了refresh方法流程
抽象方法：
- refreshBeanFactory：加载BeanDefinition
- getBeanFactory：返回BeanFactory