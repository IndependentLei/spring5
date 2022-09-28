package com.ji.spring5.test.aop.aspectj;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class AspectjApplication {
    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("aspect1",Aspect1.class);
        context.registerBean("config",Config.class);
        context.registerBean(ConfigurationClassPostProcessor.class);
        context.registerBean(AnnotationAwareAspectJAutoProxyCreator.class); // 将高级切面 转为 低级切面

        context.refresh();

        for (String name : context.getBeanDefinitionNames()) {
            System.out.println("definitionName >>>>>>>> "+name);
        }

        AnnotationAwareAspectJAutoProxyCreator creator = context.getBean(AnnotationAwareAspectJAutoProxyCreator.class);
//        try {
//            Method findEligibleAdvisors = AbstractAdvisorAutoProxyCreator.class.getDeclaredMethod("findEligibleAdvisors",Class.class,String.class);
//            findEligibleAdvisors.setAccessible(true);
//            Object invoke = findEligibleAdvisors.invoke(AbstractAdvisorAutoProxyCreator.class, Target2.class,"Target2");
//            for (Advisor advisor : invoke) {
//                System.out.println(advisor);
//            }
//        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
//            e.printStackTrace();
//        }



//        List<Advisor> advisors = creator.findEligibleAdvisors(Target2.class, "target2");
//        for (Advisor advisor : advisors) {
//            System.out.println(advisor);
//        }
//        /**
//         * 第二个重要方法 wrapIfNecessary
//         * 1、它内部调用findEligibleAdvisors，只要返回集合不空，则表示需要创建代理
//         */
//
//        Object o1= creator.wrapIfNecessary(new Target1(),"target1","target1");
//        System.out.println(o1);
//        Object o2= creator.wrapIfNecessary(new Target2(),"target2","target2");
//        System.out.println(02);
    }

    static class Target1{
        public void get(){
            System.out.println("get");
        }
    }

    static class  Target2{
        public void set(){
            System.out.println("set");
        }
    }

    @Aspect
    @Order(1)
    static class Aspect1{
        @After("execution(* get())")
        public void after(){
            System.out.println("after");
        }

        @Before("execution(* get())")
        public void before(){
            System.out.println("before");
        }
    }

    @Configuration
    static class Config{

        @Bean
        public MethodInterceptor methodInterceptor(){
            return new MethodInterceptor() {
                @Override
                public Object invoke(MethodInvocation methodInvocation) throws Throwable {
                    System.out.println("before--------");
                    Object result = methodInvocation.proceed();
                    System.out.println("after--------");
                    return result;
                }
            };
        }

        @Bean
        public DefaultPointcutAdvisor DefaultPointcutAdvisor(@Qualifier("methodInterceptor") MethodInterceptor methodInterceptor){
            AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
            pointcut.setExpression("execution(* get())");
            return new DefaultPointcutAdvisor(pointcut,methodInterceptor);
        }
    }
}
