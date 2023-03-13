package com.yancy.springframework.beans.factory;

/**
 * Bean异常
 * @author yancy0109
 */
public class BeansException extends RuntimeException{

    public BeansException(String msg) {
        super(msg);
    }

    public BeansException(String message, Throwable cause) {
        super(message, cause);
    }
}
