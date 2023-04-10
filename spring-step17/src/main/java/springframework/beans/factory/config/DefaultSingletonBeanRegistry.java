package springframework.beans.factory.config;

import com.yancy.springframework.beans.BeansException;
import com.yancy.springframework.beans.factory.DisposableBean;
import com.yancy.springframework.beans.factory.ObjectFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 默认单例注册类
 * 实现了获取单例对象
 * 提供了供子类调用的addSingleton方法
 * @author yancy0109
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    /**
     * 单例对象容器
     * 一级缓存 普通对象
     */
    private final Map<String, Object> singletonObjects = new HashMap<>();

    /**
     * 二级缓存，提前暴漏对象，没有完全实例化的对象
     */
    protected final Map<String, Object> earlySingletonObjects = new HashMap<>();

    /**
     * 三级缓存，存放代理对象
     */
    private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>();

    /**
     * 销毁对象适配器容器
     */
    private final Map<String , DisposableBean> disposableBeans = new HashMap<>();

    /**
     * 单例对象的空对象标记
     * 用作 concurrentMap 标记值 （不支持 NULL）
     */
    protected static final Object NULL_OBJECT = new Object();

    @Override
    public Object getSingleton(String beanName) {
        // 在一级缓存寻找对象
        Object singletonObject = singletonObjects.get(beanName);
        // 一级缓存不存在对象
        if (null == singletonObject) {
            // 在二级缓存寻找半成品对象
            singletonObject = earlySingletonObjects.get(beanName);
            // 二级缓存不存在目标对象 则该对象为代理对象 只有代理对象才会放到三级缓存中
            if (null == singletonObject) {
                // 找到三级缓存
                ObjectFactory<?> singletonFactory = singletonFactories.get(beanName);
                if (singletonFactory != null) {
                    singletonObject = singletonFactory.getObject();
                    // 把三级缓存中的代理对象的真实对象获取出来，放入二级缓存中
                    earlySingletonObjects.put(beanName, singletonObject);
                    singletonFactories.remove(beanName);
                }
            }
        }
        return singletonObject;
    }

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
        earlySingletonObjects.remove(beanName);
        singletonFactories.remove(beanName);
    }

    protected void addSingletonFactory(String beanName,ObjectFactory<?> singletonFactory) {
        if (!this.singletonFactories.containsKey(beanName)) {
            this.singletonFactories.put(beanName, singletonFactory);
            this.earlySingletonObjects.remove(beanName);
        }
    }

    public void registerDisposableBean(String beanName, DisposableBean bean) {
        disposableBeans.put(beanName, bean);
    }

    @Override
    public void destroySingletons() {
        Set<String> keySet = this.disposableBeans.keySet();
        Object[] disposableBeanNames = keySet.toArray();
        for (int i = disposableBeanNames.length -1; i >= 0; i--) {
            Object beanName = disposableBeanNames[i];
            DisposableBean disposableBean = disposableBeans.remove(beanName);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
            }
        }
    }
}
