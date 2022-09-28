package com.ji.spring5.test.aop.aspectj;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Advisor;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

import javax.annotation.PostConstruct;

public class Aspectj1Application {
    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean(ConfigurationClassPostProcessor.class);
        context.registerBean(Config.class);

        context.refresh();
        context.close();

        /**
         * 创建 -> （*）依赖注入 -> 初始化(*)
         * 1、代理的创建时机
         * 2、初始化之后（无循环依赖时候）
         * 3、实例创建后，依赖注入前（有循环依赖时），并暂存在二级缓存
         * 4、依赖注入与初始化不应该被增强，任应该施加在原始对象上
         */
    }

    @Configuration
    static class Config{
        @Bean
        public AnnotationAwareAspectJAutoProxyCreator annotationAwareAspectJAutoProxyCreator(){
            return new AnnotationAwareAspectJAutoProxyCreator();
        }

        @Bean
        public AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor(){
            return new AutowiredAnnotationBeanPostProcessor();
        }

        @Bean
        public CommonAnnotationBeanPostProcessor commonAnnotationBeanPostProcessor(){
            return new CommonAnnotationBeanPostProcessor();
        }

        @Bean
        public Advisor advisor(MethodInterceptor methodInterceptor){
            AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
            pointcut.setExpression("execution(* foo())");
            return  new DefaultPointcutAdvisor(pointcut,methodInterceptor);
        }

        @Bean
        public MethodInterceptor methodInterceptor(){
            return methodInvocation -> {
                System.out.println(">>>>>> before >>>>>>");
                return methodInvocation.proceed();
            };
        }

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
        public Bean1(){
            System.out.println(">>>>> start bean1");
        }
        @PostConstruct
        public void init(){
            System.out.println(">>>>> init  bean1");
        }

        @Autowired
        public void setBean2(Bean2 bean2){
            System.out.println(">>>>> setBean2 bean1 "+bean2.getClass());
        }
    }

    static class Bean2{
        private Bean1 bean1;

        public void foo(){}
        public Bean2(){
            System.out.println(">>>>> start bean2");
        }

        @Autowired
        public void setBean1(Bean1 bean1){
            System.out.println(">>>>> setBean1 bean2 "+bean1.getClass());
            this.bean1 = bean1;
        }

        @PostConstruct
        public void init(){
            System.out.println(">>>>> init  bean2");
        }
    }
}
