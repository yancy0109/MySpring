package com.yancy.springframework.test.event;

import com.yancy.springframework.context.ApplicationListener;
import com.yancy.springframework.context.event.ContextClosedEvent;

/**
 * @author yancy0109
 */
public class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {

    @Override
    public void onApplication(ContextClosedEvent event) {
        System.out.println("关闭事件：" + this.getClass().getName());
    }
}
