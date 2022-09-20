package com.ji.spring5.test.beanLifeCycle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Description :
 * @ClassName : TestBeanLifecycle
 * @Author : jdl
 * @Create : 2022-09-15 21:15
 */
@SpringBootApplication
public class TestBeanLifecycle {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TestBeanLifecycle.class, args);
        context.close();
    }
}
