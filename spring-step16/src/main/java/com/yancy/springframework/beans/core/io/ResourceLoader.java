package com.yancy.springframework.beans.core.io;

/**
 * 包装资源加载器接口类
 * 对不同资源类加载进行包装
 */
public interface ResourceLoader {

    /**
     * ClassPath加载方式伪前缀
     */
    String CLASSPATH_URL_PREFIX = "classpath:";

    Resource getResource(String location);
}
