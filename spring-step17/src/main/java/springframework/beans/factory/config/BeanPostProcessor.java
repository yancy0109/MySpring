package springframework.beans.factory.config;

import com.yancy.springframework.beans.BeansException;

public interface BeanPostProcessor {

    /**
     * 在Bean对象执行初始化方法之前执行
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;


    /**
     * 在Bean对象执行初始化方法之后执行
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}
