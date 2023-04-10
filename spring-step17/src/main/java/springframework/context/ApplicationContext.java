package springframework.context;

import com.yancy.springframework.beans.core.io.ResourceLoader;
import com.yancy.springframework.beans.factory.HierarchicalBeanFactory;
import com.yancy.springframework.beans.factory.ListableBeanFactory;

public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher {

}
