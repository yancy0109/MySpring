package springframework.beans.factory;

/**
 * 容器感知类：感知到所属的ClassLoader
 * @author yancy0109
 */
public interface BeanClassLoaderAware extends Aware {

    void setBeanClassLoader(ClassLoader classLoader);
}
