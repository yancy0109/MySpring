package springframework.beans.factory;

import com.yancy.springframework.beans.BeansException;

/**
 * 可以返回对象类接口
 * @author yancy0109
 */
public interface ObjectFactory <T> {

    T getObject() throws BeansException;
}
