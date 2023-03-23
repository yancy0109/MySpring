package com.yancy.springframework.context;

import java.util.EventListener;

public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {

    /**
     * 处理 一个 Application Event
     * @param event
     */
    void onApplication(E event);
}
