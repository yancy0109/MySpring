package com.yancy.springframework.test.event;

import com.yancy.springframework.context.ApplicationListener;

import java.util.Date;

/**
 * @author yancy0109
 */
public class CustomEventListener implements ApplicationListener<CustomEvent> {

    @Override
    public void onApplication(CustomEvent event) {
        System.out.println("收到: " + event.getSource() + "消息, 时间: " + new Date());
        System.out.println("消息: " + event.getId() + ": " + event.getMessage());
    }
}
