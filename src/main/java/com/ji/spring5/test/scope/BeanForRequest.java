package com.ji.spring5.test.scope;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
@Scope("request")
public class BeanForRequest {
    @PreDestroy
    public void destroy(){
        System.out.println(" BeanForRequest destroy ");
    }
}
