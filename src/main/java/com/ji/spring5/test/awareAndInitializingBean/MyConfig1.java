package com.ji.spring5.test.awareAndInitializingBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class MyConfig1 {


    /**
     * Java配置类包含 BeanFactoryPostProcessor 的情况，
     * 因此要创建其中的BeanFactoryPostProcessor必须提前创建Java配置类，
     * 而此时的BeanPostProcessor还未准备好，导致 @Autowired等注解失效
     */
    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext){
        System.out.println("注入ApplicationContext >>>>>>>> "+ applicationContext);
    }

    @PostConstruct
    public  void init(){
        System.out.println("初始化 >>>>>>>> "+ this);
    }



    @Bean
    public BeanFactoryPostProcessor postProcessor(){
        return beanFactory -> System.out.println("BeanFactoryPostProcessor >>>>>>> "+ this);
    }
}
