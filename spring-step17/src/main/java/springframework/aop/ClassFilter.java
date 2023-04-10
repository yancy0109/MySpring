package springframework.aop;


/**
 * 匹配类接口
 * 用于切口找到给定的接口与目标类
 */
public interface ClassFilter {

    boolean matches(Class<?> clazz);
}
