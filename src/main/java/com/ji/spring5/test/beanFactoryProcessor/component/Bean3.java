package com.ji.spring5.test.beanFactoryProcessor.component;

import org.springframework.stereotype.Component;

@Component
public class Bean3 {
    public Bean3(){
        System.out.println("Bean3 被spring 接管");
    }
}
