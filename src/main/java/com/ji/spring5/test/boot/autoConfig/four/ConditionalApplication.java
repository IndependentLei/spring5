package com.ji.spring5.test.boot.autoConfig.four;

import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

public class ConditionalApplication {
    public static void main(String[] args) {
        // 条件装配
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("webConfig",WebConfig.class);

        context.registerBean(ConfigurationClassPostProcessor.class);

        context.refresh();

        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }

        context.close();
    }
}
