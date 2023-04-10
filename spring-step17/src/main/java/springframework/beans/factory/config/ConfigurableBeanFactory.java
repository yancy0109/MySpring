package springframework.beans.factory.config;

import com.yancy.springframework.beans.factory.HierarchicalBeanFactory;
import com.yancy.springframework.utils.StringValueResolver;

/**
 * @author yancy0109
 */
public interface ConfigurableBeanFactory extends SingletonBeanRegistry, HierarchicalBeanFactory {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    /**
     * 记录 BeanPostProcessor
     * @param beanPostProcessor
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * 添加 StringValueResolver
     * @param valueResolver
     */
    void addEmbeddedValueResolver(StringValueResolver valueResolver);

    /**
     * 处理嵌入值，如 注解属性
     * @param value
     * @return
     */
    String resolveEmbeddedValueResolver(String value);

}
