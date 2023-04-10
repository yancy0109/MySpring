package springframework.beans.factory;

/**
 * 初始化接口
 * @author yancy0109
 */
public interface InitializingBean {


    /**
     * Bean 属性填充后调用
     * @throws Exception
     */
    void afterPropertiesSet() throws Exception;
}
