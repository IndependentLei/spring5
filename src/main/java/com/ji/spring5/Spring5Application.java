package com.ji.spring5;

import com.ji.spring5.entity.UserRegisteredEvent;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.core.Local;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Map;

@SpringBootApplication
public class Spring5Application {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, IOException {
        ConfigurableApplicationContext context = SpringApplication.run(Spring5Application.class, args);
        /**
         * 1.到底什么是BeanFactory
         *      - 他是ApplicationContext的父接口
         *      - 他才是Spring的核心容器，主要是ApplicationContext实现都组合了他的功能
         */
        System.out.println(context);

        /**
         * 2.BeanFactory
         *      - 表面上只有getBean
         *      - 实际上控制反转，基本的依赖注入，直至Bean的生命周期的各种功能，都有它的实现类提供
         */
//        DefaultListableBeanFactory
        // DefaultSingletonBeanReistry  bean 的存放地方  singletonObjects 一级缓存
        Field singletonObjects = DefaultSingletonBeanRegistry.class.getDeclaredField("singletonObjects");
        singletonObjects.setAccessible(true);
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        Map<String,Object> map  = (Map<String,Object>)singletonObjects.get(beanFactory);
        System.out.println("----");

//        System.out.println(context.getMessage("hi", null, Locale.CHINA)); // 国际化  由 MessageSource 这个接口实现功能

        // 内路径下使用 classPath:  项目外的使用 file:
        Resource[] resources = context.getResources("classPath:application.properties");  //  由 ResourcePatternResolver 这个接口实现功能
        for (Resource resource : resources){
            System.out.println(resource.getFilename());
        }

        Resource[] factories = context.getResources("classPath*:META-INFO/spring.factories");
        for (Resource resource : factories){
            System.out.println(resource.getFilename());
        }

        ConfigurableEnvironment environment = context.getEnvironment();
        System.out.println(environment.getProperty("java_home")); // jdk安装目录
        System.out.println(environment.getProperty("serve.port")); //


        context.publishEvent(new UserRegisteredEvent("111"));
    }


    @Bean("mmmmmmmm")
    public String myBean(){
        return "myBeawn";
    }

}
