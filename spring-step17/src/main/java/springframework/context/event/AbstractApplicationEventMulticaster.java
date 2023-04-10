package springframework.context.event;

import com.yancy.springframework.beans.BeansException;
import com.yancy.springframework.beans.factory.BeanFactory;
import com.yancy.springframework.beans.factory.BeanFactoryAware;
import com.yancy.springframework.context.ApplicationEvent;
import com.yancy.springframework.context.ApplicationListener;
import com.yancy.springframework.utils.ClassUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster, BeanFactoryAware {

    public final Set<ApplicationListener<ApplicationEvent>> applicationListeners = new LinkedHashSet<>();

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.add((ApplicationListener<ApplicationEvent>) listener);
    }

    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.remove((ApplicationListener<ApplicationEvent>) listener);
    }

    /**
     * 根据 ApplicationEvent 获取指定 ApplicationListener 集合
     * @param event
     * @return
     */
    protected Collection<ApplicationListener> getApplicationListener(ApplicationEvent event) {
        LinkedList<ApplicationListener> allListeners = new LinkedList<>();
        for (ApplicationListener<ApplicationEvent> listener : applicationListeners) {
            if (supportsEvent(listener, event)) {
                allListeners.add(listener);
            }
        }
        return allListeners;
    }

    /**
     * 监视器是否对该事件感兴趣
     * @param applicationListener
     * @param event
     * @return
     */
    private boolean supportsEvent(ApplicationListener<ApplicationEvent> applicationListener, ApplicationEvent event) {
        Class<? extends ApplicationListener> listenerClass = applicationListener.getClass();

        // 按照 CglibSubclassInstantiationStrategy SimpleInstantiationStrategy 不同的实例化类型 需要判断后获取其真实 Class
        Class<?> targetClass = ClassUtils.isCglibProxyClass(listenerClass) ? listenerClass.getSuperclass() : listenerClass;
        // 获取该监听器类实现的第一个泛型接口 - ApplicationListener<?>
        Type genericInterface = targetClass.getGenericInterfaces()[0];
        // 获取实际类型参数
        Type actualTypeArgument = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
        String className = actualTypeArgument.getTypeName();
        Class<?> eventClassName;
        try {
            eventClassName = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new BeansException("Wrong event class name: " + className);
        }
        // 判定 eventClassName 对象所表示的类或接口与指定 event.getClass() 参数所表示的类/接口是否相同，或是否是其超类或超接口
        // isAssignableFrom --> 判断 子类与父类的关系 / 接口实现类与接口的关系
        // return true  --> B 可以转为 A
        return eventClassName.isAssignableFrom(event.getClass());
    }
}
