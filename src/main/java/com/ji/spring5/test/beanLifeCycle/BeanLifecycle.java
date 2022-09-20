package com.ji.spring5.test.beanLifeCycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @Description :
 * @ClassName : BeanLifecycle
 * @Author : jdl
 * @Create : 2022-09-15 21:18
 */
@Component
public class BeanLifecycle {
    public BeanLifecycle(){
        System.out.println("BeanLifecycle 构造");
    }

    @Autowired
    public void autowire(@Value("${JAVA.HOME}") String home){
        System.out.println("依赖注入:"+home);
    }

    @PostConstruct
    public void init(){
        System.out.println("初始化");
    }

    @PreDestroy
    public void destroy(){
        System.out.println("销毁");
    }
}
