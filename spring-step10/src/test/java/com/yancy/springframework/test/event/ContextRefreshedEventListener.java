package com.yancy.springframework.test.event;

import com.yancy.springframework.context.ApplicationListener;
import com.yancy.springframework.context.event.ContextClosedEvent;
import com.yancy.springframework.context.event.ContextRefreshedEvent;

/**
 * @author yancy0109
 */
public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplication(ContextRefreshedEvent event) {
        System.out.println("刷新事件：" + this.getClass().getName());
    }
}
