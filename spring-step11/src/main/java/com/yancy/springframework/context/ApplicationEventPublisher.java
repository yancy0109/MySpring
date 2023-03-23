package com.yancy.springframework.context;

/**
 * 事件发布者接口
 * @author yancy0109
 */
public interface ApplicationEventPublisher {

    void publishEvent(ApplicationEvent event);
}
