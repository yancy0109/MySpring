package springframework.context.event;

import com.yancy.springframework.beans.factory.BeanFactory;
import com.yancy.springframework.context.ApplicationEvent;
import com.yancy.springframework.context.ApplicationListener;

/**
 * @author yancy0109
 */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void multicastEvent(ApplicationEvent event) {
        for (final ApplicationListener listener : getApplicationListener(event)) {
            listener.onApplication(event);
        }
    }
}
