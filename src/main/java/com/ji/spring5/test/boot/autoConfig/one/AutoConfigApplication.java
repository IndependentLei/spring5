package com.ji.spring5.test.boot.autoConfig.one;

import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

public class AutoConfigApplication {
    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        context.getDefaultListableBeanFactory().setAllowBeanDefinitionOverriding(false); // 是否允许 bean 覆盖
        context.registerBean("webConfig", WebConfig.class);

        context.registerBean(ConfigurationClassPostProcessor.class);

        context.refresh();

        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }

        WebConfig1.TestBean2 testBean2 =(WebConfig1.TestBean2) context.getBean("testBean2");
        System.out.println(testBean2);
    }
}
