package springframework.aop.framework.autoproxy;

import com.yancy.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import com.yancy.springframework.aop.framework.ProxyFactory;
import com.yancy.springframework.beans.BeansException;
import com.yancy.springframework.beans.PropertyValues;
import com.yancy.springframework.beans.factory.BeanFactory;
import com.yancy.springframework.beans.factory.BeanFactoryAware;
import com.yancy.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.yancy.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 自动创建代理者
 * @author yancy0109
 */
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;

    /**
     * 如果 创建的 Bean 对象，被其他属性循环依赖，则会主动调用了 创建代理类逻辑，该类已经不需要再去创建代理类
     * 可以直接注册 Singleton，而代理类对象已经被其他类依赖时创建并放入了 DefaultSingletonBeanRegister#earlySingletonObjects
     */
    private final Set<Object> earlyProxyReferences = Collections.synchronizedSet(new HashSet<>());

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 如果不含有 ProxyReference, 则主动创建代理类
        if (!earlyProxyReferences.contains(beanName)) {
            return wrapIfNecessary(bean, beanName);
        }
        return bean;
    }

    protected Object wrapIfNecessary(Object bean, String beanName) {
        // 如果为 基础设施类 则不需要处理 返回原对象
        if(isInfrastructureClass(bean.getClass())) return bean;
        // 实例化获取 AspectJExpressionPointcutAdvisor 对象
        Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();
        // 遍历所有 advisors
        for (AspectJExpressionPointcutAdvisor advisor : advisors) {
            ClassFilter classFilter = advisor.getPointcut().getClassFilter();
            if(!classFilter.matches(bean.getClass())) continue;
            // 如果当前类 与 切点类型匹配契合
            AdvisedSupport advisedSupport = new AdvisedSupport();
            TargetSource targetSource = new TargetSource(bean);
            // 配置原对象
            advisedSupport.setTargetSource(targetSource);
            // 配置方法拦截器
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            // 配置方法匹配器
            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
            // 是否使用 Cglib 代理
            advisedSupport.setProxyTargetClass(true);
            // 返回 ProxyFactory 对象
            return new ProxyFactory(advisedSupport).getProxy();
        }

        return bean;
    }

    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) {
        earlyProxyReferences.add(beanName);
        return wrapIfNecessary(bean, beanName);
    }

    /**
     * 是否为基础设施类
     * @param beanClass
     * @return
     */
    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass) || Pointcut.class.isAssignableFrom(beanClass) || Advisor.class.isAssignableFrom(beanClass);
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        return pvs;
    }
}
