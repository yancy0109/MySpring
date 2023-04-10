package springframework.beans.factory;

/**
 * 销毁方法接口
 * @author yancy0109
 */
public interface DisposableBean {


    /**
     * Bean 销毁方法
     * @throws Exception
     */
    void destroy() throws Exception;
}
