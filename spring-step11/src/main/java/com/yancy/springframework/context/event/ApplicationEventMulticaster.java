package com.yancy.springframework.context.event;

import com.yancy.springframework.context.ApplicationEvent;
import com.yancy.springframework.context.ApplicationListener;

public interface ApplicationEventMulticaster {

    void addApplicationListener(ApplicationListener<?> listener);

    void removeApplicationListener(ApplicationListener<?> listener);

    /**
     * 广播事件方法
     * @param event
     */
    void multicastEvent(ApplicationEvent event);
}
