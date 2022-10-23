package com.ji.spring5.test.boot.autoConfig.two;

import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.support.GenericApplicationContext;

public class AopAutoConfigApplication {
    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        AnnotationConfigUtils.registerAnnotationConfigProcessors(context.getDefaultListableBeanFactory());
        context.registerBean("webConfig", WebConfig.class);
        context.refresh();

        context.close();
    }
}
