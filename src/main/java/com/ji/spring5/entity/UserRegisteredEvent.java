package com.ji.spring5.entity;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

/**
 * @Description :
 * @ClassName : UserRegisteredEvent
 * @Author : jdl
 * @Create : 2022-09-13 21:59
 */
public class UserRegisteredEvent extends ApplicationEvent {
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public UserRegisteredEvent(Object source) {
        super(source);
    }
}
