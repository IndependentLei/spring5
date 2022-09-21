package com.ji.spring5.test.awareAndInitializingBean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;

public class MyBean implements BeanNameAware , ApplicationContextAware , InitializingBean {
    /**
     *
     * BeanNameAware , ApplicationContextAware 先执行 这两个的回调 ，后执行 InitializingBean 的回调
     */
    @Override
    public void setBeanName(String name) {
        System.out.println("beanName >>>>>>> "+name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("applicationContext 实现ApplicationContextAware接口注入 >>>>>>> "+applicationContext); // GenericApplicationContext
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean >>>>>>> "+this);
    }

    // 不生效，需要添加后置处理器
    @Autowired
    public void setAApplicationContext(ApplicationContext applicationContext){
        System.out.println("applicationContext 使用@Autowired注解注入 >>>>>>> "+applicationContext);
    }

    // 不生效，需要添加后置处理器
    @PostConstruct
    public void postConstruct(){
        System.out.println("postConstruct 使用PostConstruct注解注入 >>>>>>> ");
    }
}
