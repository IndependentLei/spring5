package com.ji.spring5.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @Description :
 * @ClassName : TestBeanFactory
 * @Author : jdl
 * @Create : 2022-09-13 22:16
 */
public class TestBeanFactory {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        /**
         * bean的定义 （class、scope、初始化、销毁）
         */

        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(config.class)
                .setScope("singleton").getBeanDefinition();
        beanFactory.registerBeanDefinition("config",beanDefinition);
        // 给BeanFactory添加一些常用的后处理器
        AnnotationConfigUtils.registerAnnotationConfigProcessors(beanFactory);


        /** 后处理器
         * org.springframework.context.annotation.internalConfigurationAnnotationProcessor  处理@Configuration注解的Bean
         * org.springframework.context.annotation.internalAutowiredAnnotationProcessor      处理@Autowired、@Value注解的Bean
         * org.springframework.context.annotation.internalCommonAnnotationProcessor         处理@Resrous注解的Bean
         * org.springframework.context.event.internalEventListenerProcessor
         * org.springframework.context.event.internalEventListenerFactory
         */
        Map<String, BeanFactoryPostProcessor> beansOfType = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class); // key为bean的名字,value为bean的后置处理器
        // BeanFactory 后处理器主要功能，补充了一些 bean 定义
        beansOfType.values().forEach(beanFactoryPostProcessor -> beanFactoryPostProcessor.postProcessBeanFactory(beanFactory)); // bean工厂的后置处理器

        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }

        Map<String, BeanPostProcessor> beansOfType1 = beanFactory.getBeansOfType(BeanPostProcessor.class);
        beansOfType1.values().forEach(beanFactory::addBeanPostProcessor); // 加入bean工厂
        beanFactory.preInstantiateSingletons(); // 准备好所有的单例bean
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Bean2 bean = beanFactory.getBean(Bean1.class).getBean2();
        /**
         * 学到了什么：
         *      1、beanFactory 不会做的事
         *          1、不会主动调用BeanFactory 后处理器
         *          2、不会主动添加Bean后处理器
         *          3、不会主动初始化单例
         *          4、不会计息beanFactory 还不会解析 ${} 与 #{}
         *      2、bean后处理器会有排序的逻辑
         */
//        AnnotationConfigUtils 中的 registerAnnotationConfigProcessors 为注入bean的方法
        /**
         * CommonAnnotationBeanPostProcessor 中的 order 属性是后处理器被执行的顺序   Ordered.LOWEST_PRECEDENCE - 3
         * AutowiredAnnotationBeanPostProcessor 中的 order 属性是后处理器被执行的顺序 Ordered.LOWEST_PRECEDENCE - 2
         * 越大的执行越前
         */
    }



    @Configuration
    static  class config{
        @Bean
        public Bean1 bean1(){
            return new Bean1();
        }


        @Bean
        public Bean2 bean2(){
            return new Bean2();
        }
    }



    static class Bean1{
        @Autowired
        Bean2 bean2;
        public Bean1(){
            System.out.println("构造 ---> bean1");
        }
        public Bean2 getBean2(){
            return this.bean2;
        }
    }

    static class Bean2{

        public Bean2(){
            System.out.println("构造 ---> bean2");
        }
    }
}
