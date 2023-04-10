package springframework.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import com.yancy.springframework.beans.BeansException;
import com.yancy.springframework.beans.factory.DisposableBean;
import com.yancy.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Method;

/**
 * 销毁方法适配器
 * 接口 / 配置
 * @author yancy0109
 */
public class DisposableBeanAdapter implements DisposableBean {

    private final Object bean;

    private final  String beanName;

    private String destroyMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }


    @Override
    public void destroy() throws Exception {
        // 实现接口 DisposableBean
        if (bean instanceof DisposableBean) {
            ((DisposableBean) bean).destroy();
        }

        // 读取配置信息中 destroy-method方法 并判断避免二次执行销毁操作
        if (StrUtil.isNotEmpty(destroyMethodName) && !(bean instanceof DisposableBean && "destroy".equals(this.destroyMethodName))) {
            Method destroyMethod = bean.getClass().getMethod(destroyMethodName);
            if (null == destroyMethod) {
                throw new BeansException("Couldn't find a destroy method named '" + destroyMethodName + "' on bean with name '" + beanName + "'");
            }
            destroyMethod.invoke(bean);
        }
    }
}
