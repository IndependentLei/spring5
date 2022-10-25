package com.ji.spring5.test.boot.autoConfig.three;

import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebApplicationContext;

public class WebServiceAutoConfigApplication {
    public static void main(String[] args) {
        AnnotationConfigServletWebApplicationContext context = new AnnotationConfigServletWebApplicationContext();
        context.registerBean(WebConfig.class);
        context.refresh();
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName + "------->" + context.getBeanDefinition(beanDefinitionName).getResourceDescription());
        }
        context.close();
    }
}
