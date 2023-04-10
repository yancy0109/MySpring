### STEP17



#### 目的

实现数据类型转换工厂设计与实现，在 Bean 配置流程加入属性的类型转换。

#### 实现

实现 ConvertionService，并在 BeanFactory 中进行注册，在实例化对象设置属性时，获取 ConvertionService，如果找到适合的 Converter，则调用 Converter 进行属性转换。

类说明：

- Converter：转换器接口
- GenericConverter：通用转换器接口
- ConverterFactory：转换器工厂，提供实现 Converter 接口的类
- ConverterRegistry：对 Converter 进行管理的接口

- ConversionService：转换服务接口
- GenericConversionService：实现了 GenericConverter 与 ConverterRegistry，对 Converter 进行了管理，并提供了适配器类对 Converter，GenericConverter，ConverterFactory 适配为 GenericConverter 类，进行统一管理调用。
- StringToNumberConverterFactory：ConverterFactory 工厂实现类
- DefaultConversionService：继承了 GenericConversionService，在父类基础上默认初始化了已有 Converter。

在 Spring 注册服务的方法为 提供 ConversionServiceFactoryBean 类，实现了 FactoryBean 与 InitializingBean 接口，实例化后调用初始化方法实例化了一个 GenericConversionService 对象，用于进行 Converter 注册（Converter 通过 属性注入 的方式，我们需要提供一个 Converters 对象，实现 Converter 接口的集合会在实例化后配置为属性），最终得到了 GenericConversionService 对象。

这个服务对象的注册发生在 Application 在初始化完成后，首先会读取 BeanDefinition 判断是否提供了 conversionService，如果有，则会提前实例化，再进行各种对象的提前单例化。





