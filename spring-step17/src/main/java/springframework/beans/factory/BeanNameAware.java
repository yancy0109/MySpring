package springframework.beans.factory;

import com.yancy.springframework.beans.BeansException;

/**
 * 容器感知类：感受到所属的BeanName
 * @author yancy0109
 */
public interface BeanNameAware extends Aware {

    void setBeanName(String name) throws BeansException;
}
