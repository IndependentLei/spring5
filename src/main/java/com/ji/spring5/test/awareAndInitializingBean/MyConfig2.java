package com.ji.spring5.test.awareAndInitializingBean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig2 implements BeanNameAware, ApplicationContextAware {
    @Override
    public void setBeanName(String name) {
        System.out.println("beanName MyConfig2 >>>>>>> "+name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("applicationContext MyConfig2 实现ApplicationContextAware接口注入 >>>>>>> "+applicationContext); // GenericApplicationContext
    }
}
