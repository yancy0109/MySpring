package java.com.yancy.springframework.beans.factory.support;

import com.yancy.springframework.beans.BeansException;
import com.yancy.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * 实例化策略接口
 */
public interface InstantiationStrategy {

    /**
     * 实例化接口
     * @param beanDefinition    Bean对象
     * @param beanName          Bean名称
     * @param constructor       Bean构造函数
     * @param args              Bean构造函数传参
     * @return                  返回对象
     * @throws BeansException   BeansException。
     */
    Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor constructor, Object[] args) throws BeansException;

}
