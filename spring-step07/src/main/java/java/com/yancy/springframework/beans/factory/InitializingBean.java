package java.com.yancy.springframework.beans.factory;

import java.com.yancy.springframework.beans.BeansException;

/**
 * 初始化接口
 * @author yancy0109
 */
public interface InitializingBean {


    void afterPropertiesSet() throws BeansException;
}
